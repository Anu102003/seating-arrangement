import React, { createRef, useEffect, useState } from 'react'
import { colorList } from '../../../constants/colorList'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faCloudArrowDown, faSquareCheck} from '@fortawesome/free-solid-svg-icons'
import { faSquare } from '@fortawesome/free-regular-svg-icons'
import { createFileName, useScreenshot } from 'use-react-screenshot'
import "./allocationLayout.scss"

export const AllocationLayout = ({items,layout,allocation}) => {
    const [toggleSelected, setToggleSelected] = useState(allocation.teamData.teams.map((e) => (
        e.teamCode
    )))
    const [teamKeyList, setTeamKeyList] = useState([])
    const ref = createRef(null)

    const handleToggle = (code) => {
        if (!toggleSelected.includes(code)) {
            setToggleSelected((prev) => [...prev, ...code])
        } else {
            setToggleSelected(prev => {
                return prev.filter(toggleSelected => toggleSelected !== code)
            })
        }
    }

    const handleReturnColor = (teamKeyValue) => {
        const filteredValue = teamKeyValue?.replace(/\d/g, '');
        console.log(filteredValue)
        for (let i = 0; i < teamKeyList.length; i++) {
            if (filteredValue === teamKeyList[i] && toggleSelected.includes(filteredValue)) {
                return colorList[i];
            }
        }
        return "grey";
    };

    const [image, takeScreenshot] = useScreenshot({
        type: 'image/jpeg',
        quality: 1.0,
    })
    const download = (image, { name = 'sample', extension = 'jpg' } = {}) => {
        const a = document.createElement('a')
        a.href = image
        a.download = createFileName(extension, name)
        a.click()
    }
    const handleDownload = () => {
        takeScreenshot(ref.current).then(download)
    }
    useEffect(() => {
        const tempTeamKeyList = [];
        allocation?.teamData.teams.forEach((team) => {
            tempTeamKeyList.push(team?.teamCode);
        });
        setTeamKeyList(tempTeamKeyList);
    }, [allocation]);
  return (
    <div className='allocation-layout'>
    <p className='h-3'><span className='h-2'>Algorithm Preference : </span>{items.algorithmPref}</p>
    <p className='h-3'><span className='h-2'>Algorithm Type :</span> {items.allocationType}</p>
    <p className='h-3'>
        <button className="download-btn" onClick={handleDownload}>
            <FontAwesomeIcon icon={faCloudArrowDown} />Download</button></p>

    <div className='allocation-team-list' ref={ref}>
        <div className='table-wrapper' >
            <table className="MyTable">
                <tbody>
                    {items.allocationLayout?.map((row, i) => {
                        return (
                            <tr key={i}>
                                {row.map((value, j) => {
                                    return (
                                        <>

                                            <td
                                                key={j}
                                                className="grid-box"
                                                style={{
                                                    backgroundColor:
                                                        layout?.[i][j] === 1
                                                            ? handleReturnColor(value)
                                                            :
                                                            "#f1f2f6",
                                                }}
                                            >
                                                {
                                                    toggleSelected.includes(value?.replace(/\d/g, '')) &&
                                                    <>{value}</>
                                                }
                                            </td>
                                        </>
                                    );
                                })}
                            </tr>
                        );
                    })}
                </tbody>
            </table>
        </div>
        <div className='team-list-wrapper'>
            <table className="team-list">
                <thead>
                    <td>Team Key</td>
                    <td>Team Name</td>
                    <td>Team Count</td>
                    <td>Color Code</td>
                    <td>Color Code</td>
                </thead>
                <tbody>
                    {allocation &&
                        allocation.teamData.teams.map((team, index) => {
                            return (
                                <tr>
                                    <td>{team.teamCode}</td>
                                    <td>{team.teamName}</td>
                                    <td>{team.teamCount}</td>
                                    <td>
                                        <div className='color-code-wrapper'>
                                            <div className="color-code" style={{ backgroundColor: colorList[index] }}></div>
                                        </div>
                                    </td>
                                    <td>
                                        {
                                            !toggleSelected.includes(team.teamCode) ?
                                                <FontAwesomeIcon icon={faSquare} size='xl' style={{ cursor: "pointer" }} onClick={() => handleToggle(team.teamCode)} color='gray' /> :
                                                <FontAwesomeIcon icon={faSquareCheck} size='xl' style={{ cursor: "pointer" }} onClick={() => handleToggle(team.teamCode)} color='#0578F9' />
                                        }
                                    </td>
                                </tr>
                            );
                        })}
                </tbody>
            </table>
        </div>
    </div>
</div>
  )
}
