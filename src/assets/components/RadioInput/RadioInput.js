import React, { useEffect } from 'react'
import "./RadioInput.scss"

export const RadioInput = ({ number,name, preference, handlePrefOnClick, label, handleSubmit }) => {
  // const radioClick=()=>{
  //   setPreference(number)
  // }
  // const radioChange=()=>{
  //   handleSubmit()
  // }
  // console.log(number)
  return (
    <label className="radio-btn">
      <input
        type="radio"
        name={name}
        checked={preference === number}
        onClick={() => { handlePrefOnClick(number) }}
      // onChange={radioChange}
      />
      {label}
    </label>
  )
}
