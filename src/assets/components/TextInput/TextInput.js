import React from 'react'
import "./TextInput.scss"
export const TextInput = ({type,name,value,availableSpaces,space,handleOnChange,index}) => {
   
    return (
        <input
            type={type}
            name={name}
            value={value}
            // {type!=="text" &&}
            min="0"
            max={availableSpaces}
            disabled={space <= 0}
            className="input-box"
            autoSave='false'
            placeholder={`Enter Team ${index + 1} ${type==="text"?"Name":"Count"}`}
            onChange={(e) => {handleOnChange(e, index)}}
        />
    )
}
