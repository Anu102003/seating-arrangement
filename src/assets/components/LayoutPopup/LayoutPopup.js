import React, { useEffect, useState } from 'react'
import "./layoutPopup.scss"

export const LayoutPopup = ({ layoutOptions, optionSelected, setOptionSelected, count, setPopup, setSubmit,deletePopup }) => {

    const [error, setError] = useState(false);
    useEffect(() => {
        if(!deletePopup){

                function handleOption() {
                    if (optionSelected === "") {
                    } else {
                        setSubmit(true);
                        setPopup(false);
                    }
                }
        
                if (optionSelected !== "") {
                    handleOption();
                }
        }
    }, [optionSelected, setSubmit, setPopup]);
    return (
        <div className='layout-popup'>
            {
                layoutOptions ?
                    <>
                        <p className='p-2'>Select any Options :</p>
                        <div className='options-btn'>
                            <button onClick={() => { setOptionSelected("Allocation") }}>Allocation</button>
                            <button onClick={() => { setOptionSelected("Seating") }}>Seating</button>
                        </div>
                    </>
                    :
                    <>
                        <p className='head'>Are you sure you want to delete this layout?</p>
                        <p className='p-1'>This will delete the layout permanently, you cannot undo this action.</p>
                        <div className="home__buttons">
                            <button className="cancel-btn" onClick={() => { setPopup(false) ; document.body.style.overflow = "unset" }}>Cancel</button>
                            <button className="delete-btn" onClick={() => setSubmit(true)}>Yes, Delete it</button>
                        </div>
                    </>
            }
        </div>
    )
}
