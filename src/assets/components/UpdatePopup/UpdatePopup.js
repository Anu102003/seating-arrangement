import React, {  useState } from 'react'
import "./updatePopup.scss"
import { Cell } from '../Cell/Cell'
import { updateLayoutApi } from '../../../actions/ApiCall'
import { useNavigate } from 'react-router-dom'

export const UpdatePopup = ({ layoutSeleted,setPopup }) => {
    const companyName = localStorage.getItem('companyName');
    const navigate = useNavigate()
    const [layoutData, setLayoutData] = useState(layoutSeleted.companyLayout);

    const updateCellValue = (rowIndex, colIndex, newValue) => {
        const updatedLayout = [...layoutData];
        updatedLayout[rowIndex][colIndex] = newValue;
        setLayoutData(updatedLayout);
    };
    console.log(layoutSeleted.layoutId, layoutData, companyName
    )

    const handleSubmit = async () => {
        try {
            const res = await updateLayoutApi(
                layoutSeleted.layoutId,
                companyName,
                layoutData
                )
                if(res.message==="updates done"){
                    navigate("/operations")
                    setPopup(false)
                    document.body.style.overflow = "unset"
                }
        } catch (err) {
            console.log("Error in updating", err)
        }
    }

    return (
        <div className='update-popup-page'>
            <p className='heading'>Update Layout</p>
            <div className='table'>
            {layoutData?.map((row, rowIndex) => (
                <div key={rowIndex} className="row">
                    {row?.map((cell, colIndex) => (
                        <Cell
                            update={true}
                            key={colIndex}
                            cellValue={cell}
                            updateValue={updateCellValue}
                            rowIndex={rowIndex}
                            colIndex={colIndex}
                        />
                    ))}
                </div>
            ))}     
            </div>
           <div className='submit-wrapper'>
            <button className='submit-btn' onClick={handleSubmit}>Submit</button>
           </div>
        </div>
    )
}
