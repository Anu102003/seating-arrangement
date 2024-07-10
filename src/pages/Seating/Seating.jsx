import { useContext, useEffect, useState } from "react";
import "./Seating.scss";
import { Link, json, useLocation, useNavigate } from "react-router-dom";
import { colorList } from "../../constants/colorList";
import { allocationApi, csvFileApi } from "../../actions/ApiCall";
import { RadioInput } from "../../assets/components/RadioInput/RadioInput";
import { TextInput } from "../../assets/components/TextInput/TextInput";
import { faArrowUpFromBracket, faCircleCheck, faClose } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { ErrorPopup } from "../../assets/components/ErrorPopup/ErrorPopup";
import { TableContainer } from "../../assets/components/Table/Table";

const Seating = () => {

  const location = useLocation();
  const res = location.state.data;
  const layoutId = location.state.layoutId;
  let availableSpaces = location.state.availableSpaces;
  const [isOutputGenerated, setIsOutput] = useState(false);
  const [outputArray, setOutputArray] = useState();

  const [file, setFile] = useState("");
  const formData = new FormData();
  const [csvData, setCsvData] = useState()

  const [teamList, setTeamList] = useState([]);
  const [teamNameList, setTeamNameList] = useState([]);
  const [teamKeyList, setTeamKeyList] = useState([]);
  const [preference, setPreference] = useState(2);
  const [algorithmPref, setAlgorithmPref] = useState(1);
  const [orderedTeamList, setOrderedTeamList] = useState([]);
  const [space, setSpace] = useState(availableSpaces);
  const [filespace, setFileSpace] = useState();
  const [inCorrectFile, setInCorrectFile] = useState(false);
  const [error, setError] = useState({
    file: false,
    addTeam: false,
    outOfSpace: false,
    fileDataIncorrect: false,
  });
  console.log(teamKeyList)
  const layOutDto = {
    layoutId: layoutId,
    teamDtoList: teamList,
    preference: preference,
    algorithmPref: algorithmPref
  };

  //file handling
  const handleClick = () => {
    document.getElementById('fileInput').click();
    setTeamList([])
  };

  const handleFile = (e) => {
    setFile(e.target.files[0]);
  };
  formData.append('file', file);


  //handing submit (api call)
  const handleSubmit = async () => {
    if (space >= 0 && layOutDto.teamDtoList.length > 0) {
      setError((prevError) => ({ ...prevError, addTeam: false, file: false, outOfSpace: false }))
      let arr = [];
      teamList?.map((team) => {
        arr.push(team?.teamName);
      });
      setTeamNameList(arr);
      const result = handleResult(layOutDto);
      const res = await allocationApi(result);
      setIsOutput(true);
      setOutputArray(res?.data?.allocation);
      let teamKeyList = [];
      res?.data?.teamReferenceList?.map((team) => {
        teamKeyList.push(team?.key);
      });
      setTeamKeyList(teamKeyList);
      setOrderedTeamList(res?.data?.teamReferenceList);
    } else {
      if (!(space >= 0)) {
        setError((prevError) => ({ ...prevError, outOfSpace: true }))
      } else if (layOutDto.teamDtoList.length < 0 && file.name !== "" || file.name !== undefined) {
        setError((prevError) => ({ ...prevError, addTeam: true }))
      } else {
        setError((prevError) => ({ ...prevError, file: true }))
      }
      setIsOutput(false);
    }
  };

  function handleResult(data) {
    return data;
  }
  // closing error 
  const handleErrorClose = () => {
    setInCorrectFile(false)
    setError((prevError) => ({ ...prevError, file: false, fileDataIncorrect: false, addTeam: false, outOfSpace: false }))
    // if (inCorrectFile) {
    //   setInCorrectFile(false)
    // } else if (!(space >= 0)) {
    //   setError((prevError) => ({ ...prevError, outOfSpace: false }))
    // }
    // else if (layOutDto.teamDtoList.length < 0 && file.name !== "" || file.name !== undefined) {
    //   setError((prevError) => ({ ...prevError, addTeam: false }))
    // } else if (availableSpaces < filespace) {
    //   setError((prevError) => ({ ...prevError, fileDataIncorrect: false }))
    // } else {
    //   setError((prevError) => ({ ...prevError, file: false }))
    // }
  }

  //close input field
  const handleCloseBtn = (index) => {
    let arr = [...teamList];
    setSpace(space + arr[index].teamCount);
    arr = arr.slice(0, index).concat(arr.slice(index + 1, arr.length));
    setTeamList(arr);
  };

  //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
  // handling csv  (api call)
  const handleFileSubmit = async () => {
    try {
      const res = await csvFileApi(formData, setInCorrectFile)
      setFileSpace(res.data.spacesOccupied)
      if (!inCorrectFile) {
        if (res.data.spacesOccupied <= availableSpaces) {
          setCsvData(res?.data?.teamDtoList)
          setError((prevError) => ({ ...prevError, fileDataIncorrect: false }))
        }
        else {
          setError((prevError) => ({ ...prevError, fileDataIncorrect: true }))
          setFile("")
          setTeamList([])
          setCsvData([])
        }
      }
    }
    catch (error) {
      console.log(error)
    }
  }


  useEffect(() => {
    if (csvData?.length > 0 && filespace <= availableSpaces) {
      setTeamList(csvData)
    }
  }, [csvData, filespace])

  //adding team list
  const handleAddTeam = () => {
    let arr = [...teamList];
    arr.push({
      teamName: "",
      teamCount: "",
    });
    setTeamList(arr);
  }

  //input handling
  const handleOnChange = (e, index) => {
    let arr = [...teamList];
    let spaces = space;
    if (teamList[index].teamCount === "" && e.target.name === "teamCount") {
      spaces -= Number(e.target.value);
      setSpace(spaces);
    }

    arr[index] = {
      ...arr[index],

      [e.target.name]:
        e.target.name === "teamName" ? e.target.value : Number(e.target.value),
    };

    if (teamList[index].teamCount !== "" && e.target.name === "teamCount") {
      spaces += teamList[index].teamCount;
      spaces -= Number(e.target.value);
      setSpace(spaces);
    }
    setTeamList(arr);
  };

  //colour 
  const handleReturnColor = (teamKeyValue) => {
    for (let i = 0; i < teamKeyList.length; i++) {
      if (teamKeyValue && teamKeyValue.includes(teamKeyList[i])) {
        return colorList[i];
      }
    }
    return "grey";
  };

  //popup close
  useEffect(() => {
    function handle(e) {
      if (e.target.className === "product-popup-parent") {
        setError(false)
        setInCorrectFile(false)
      }
    }
    window.addEventListener("click", handle)
    return () => window.removeEventListener("click", handle)
  }, [])
  console.log(preference, algorithmPref)
  return (
    <div className="seating">

      {!isOutputGenerated ?
        <div className="container-1">

          <TableContainer data={res} />

          <div className="form-wrapper">
            {
              (file?.name === undefined || file?.name === "") &&
              <>
                <p className="h-1">Available Spaces : {space}</p>
                <div className="add-div">
                  <p className="h-2">Add Team</p>
                  <button className="add-btn" onClick={handleAddTeam}>
                    + Add
                  </button>
                </div>
              </>
            }

            <div className="btn-wrapper">
              <p className="h-2">Count Priority</p>
              <RadioInput number={2} preference={preference} name="perference" handlePrefOnClick={setPreference} label="ASC" />
              <RadioInput number={1} preference={preference} name="perference" handlePrefOnClick={setPreference} label="DES" />
              <RadioInput number={3} preference={preference} name="perference" handlePrefOnClick={setPreference} label="Random" />
            </div>
            <div className="btn-wrapper">
              <p className="h-2">Algorithm prefer </p>
              <RadioInput number={1} preference={algorithmPref} name="algorithm" handlePrefOnClick={setAlgorithmPref} label="ALGORITHM 1" />
              <RadioInput number={2} preference={algorithmPref} name="algorithm" handlePrefOnClick={setAlgorithmPref} label="ALGORITHM 2" />
            </div>

            <div className="team-list-input-wrapper">
              <div className="team-list-container">
                {teamList &&
                  teamList?.map((data, index) => {
                    return (
                      <div className="input-wrapper">
                        <TextInput type="text" name="teamName" value={data.teamName} index={index} handleOnChange={handleOnChange} />
                        <TextInput type="number" name="teamCount" value={data.teamCount} availableSpaces={availableSpaces} space={space} index={index} handleOnChange={handleOnChange} />
                        {
                          (file?.name === undefined || file?.name === "") && <button
                            className="cross-btn"
                            onClick={() => handleCloseBtn(index)}>
                            X
                          </button>
                        }
                      </div>
                    );
                  })}
                <div className="input-file">
                  {
                    (file?.name === undefined || file?.name === "") &&
                    <div className="or-div">
                      <div className="line"></div>
                      <p className="h-3">OR</p>
                      <div className="line"></div>
                    </div>
                  }
                  <div className='upload-wrapper' >
                    <FontAwesomeIcon icon={faArrowUpFromBracket} size="3x" color="#387EED" onClick={handleClick} />
                    <input
                      style={{ display: 'none' }}
                      onChange={handleFile}
                      id="fileInput"
                      type="file"
                      accept=".csv, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel"
                    />
                    <button className='upload-btn' onClick={handleClick}>
                      Upload csv file
                    </button>
                    <br></br>
                    <div className="upload-file-wrapper">
                      <p className="uploaded-file">{file?.name}</p>
                      {
                        file?.name !== undefined &&
                        <button className="cross-btn" onClick={() => { setFile(""); setTeamList([]); setCsvData([]) }}>
                          X</button>
                      }
                    </div>
                  </div>
                  {
                    !(file?.name === undefined || file?.name === "") &&
                    <button onClick={handleFileSubmit} className="click-btn" style={{
                      backgroundColor: csvData?.length > 0 ? "#42CE7D" :
                        (file?.name === undefined || file?.name === "") ? "#3740a4" : "red"
                    }}>
                      <FontAwesomeIcon icon={faCircleCheck} style={{ marginRight: "0.5rem" }} />
                      {csvData?.length > 0 ? "Vertified Successfully" : "Proceed to Verify"} </button>
                  }
                </div>
              </div>
            </div>
            <div className="submit-wrapper">
              <button className="submit-btn" onClick={handleSubmit}>
                Submit
              </button>
            </div>
          </div>

        </div>
        :
        <div className="container ">
          <div className="layout-wrapper">
            <h2>Team Allocation</h2>
            <div className="btn-wrapper">
              <div className="btn-options">
                <p className="h-2">Count Priority :</p>
                <RadioInput handleSubmit={handleSubmit} number={2} preference={preference} handlePrefOnClick={setPreference} label="ASC" />
                <RadioInput handleSubmit={handleSubmit} number={1} preference={preference} handlePrefOnClick={setPreference} label="DES" />
                <RadioInput handleSubmit={handleSubmit} number={3} preference={preference} handlePrefOnClick={setPreference} label="Random" />
              </div>
            </div>
            <div className="btn-wrapper">
              <div className="btn-options">
                <p className="h-2">Algorithm prefer : </p>
                <RadioInput number={1} preference={algorithmPref} name="algorithm" handlePrefOnClick={setAlgorithmPref} label="ALGORITHM 1" />
                <RadioInput number={2} preference={algorithmPref} name="algorithm" handlePrefOnClick={setAlgorithmPref} label="ALGORITHM 2" />
              </div>
            </div>
            <div className="filter-btn-wrapper">
              <button onClick={handleSubmit} className="filter-btn">Apply Filter</button>
            </div>
            <div className="team-key-continer">
              {teamNameList && <h2>Team Keys</h2>}
              <table className="team-key-list key-table">
                <thead>
                  <td>Team Key</td>
                  <td>Team Name</td>
                  <td>Team Count</td>
                </thead>
                <tbody>
                  {orderedTeamList &&
                    orderedTeamList.map((team) => {
                      return (
                        <tr>
                          <td>{team.key}</td>
                          <td>{team.name}</td>
                          <td>{team.count}</td>
                        </tr>
                      );
                    })}
                </tbody>
              </table>
            </div>
            <table className="MyTable MyTable-2">
              <tbody>
                {outputArray?.map((row, i) => {
                  return (
                    <tr key={i}>
                      {row.map((value, j) => {
                        return (
                          <td
                            key={j}
                            className="grid-box"
                            style={{
                              backgroundColor:
                                res?.[i][j] === 1
                                  ? handleReturnColor(value)
                                  : "#f1f2f6",
                            }}
                          >
                            {value}
                          </td>
                        );
                      })}
                    </tr>
                  );
                })}
              </tbody>
            </table>

          </div>

          {/* )} */}
        </div>
      }
      {
        (error.file || error.addTeam || error.outOfSpace || error.fileDataIncorrect || inCorrectFile) &&
        <div className='product-popup-parent'>
          <div className='product-popup'>
            <div className='close-icon' onClick={handleErrorClose}>
              <FontAwesomeIcon icon={faClose} size='2xl' />
            </div>
            <ErrorPopup error={error} inCorrectFile={inCorrectFile} />
          </div>
        </div>
      }
    </div>
  );
};

export default Seating;
