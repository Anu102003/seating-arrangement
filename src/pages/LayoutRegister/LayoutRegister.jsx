import {  useState } from "react";
import "./LayoutRegister.scss";
import { useNavigate } from "react-router-dom";

const LayoutRegister = () => {

  const navigate = useNavigate()
  const companyName = localStorage.getItem('companyName');
  const [totalLayouts, setTotalLayouts] = useState(0)
  const [error, setError] = useState({
    totalLayouts: false,
  })

  const handleLayout = (e) => {
    const { name, value } = e.target
    console.log(name)
    setTotalLayouts(value);
    if (value > 0) {
      setError((prevError) => ({ ...prevError, [name]: false }))
    } else {
      setError((prevError) => ({ ...prevError, [name]: true }))
    }
  }
  const handleSubmit = () => {
    if (totalLayouts > 0) {
      navigate("/layoutform",{ state: { totalLayout:totalLayouts }})
    } else {
      if (!(totalLayouts > 0)) {
        {
          setError((prevError) => ({ ...prevError, totalLayouts: true }))
        }
      }
    }
  }
  return (
    <>
      <div className="home-page">
        <div className="home-page__left">
          <img src="https://octopod.co.in/slink/images/login.svg" />
        </div>
        <div className="home-page__right">
          <div className="home">
            <h4 className="home__heading">Layout Details </h4>
            <p className="home__head">
              <p>Total Number of Layouts</p>
              <p className="danger">*</p>
            </p>
            <div className={`home__input ${companyName !== "" && "activeInput"}`}>
              <input
                name="totalLayouts"
                type="number"
                value={totalLayouts}
                min="0"
                onChange={handleLayout}
                placeholder="Enter number of Layouts"
              />
            </div>
            {/* {
              error &&
              <p className="error">Please Enter Company Name</p>
            } */}
            {
              error.totalLayouts &&
              <p className="error">Layout should be greater than one</p>
            }
            {/* <p className="p-1"> Is company name already registered ?</p> */}

            <div className="home__buttons">
              <button className="yes-btn" onClick={handleSubmit}>Submit</button>
              {/* <button className="no-btn" onClick={handleNo}>No</button> */}
            </div>
          </div>
        </div>
      </div>
    </>
  );
};

export default LayoutRegister;
