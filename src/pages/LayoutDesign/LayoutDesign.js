import React, { useContext, useEffect, useState } from "react";
import "./LayoutDesign.scss";
import { Cell } from "../../assets/components/Cell/Cell";
import { useNavigate } from "react-router-dom";
import { LayoutPopup } from "../../assets/components/LayoutPopup/LayoutPopup";

export const LayoutDesign = ({ row, column,setUpdateData, setData, setFormSubmit, setDetails, data, totalLayout }) => {

  const navigate = useNavigate();
  const [count, setCount] = useState(0);
  const [popup, setPopup] = useState(false)
  const [newData, setNewData] = useState(null);
  const [arrayData, setArrayData] = useState({});
  const companyName = localStorage.getItem('companyName');
  const dataArray = Object.values(arrayData);
  const [submit, setSubmit] = useState(false);


  const updateValue = (rowIndex, colIndex, value) => {
    const newArrayData = { ...arrayData };
    newArrayData[rowIndex][colIndex] = value;
    setArrayData(newArrayData);
  };

  useEffect(() => {
    const initialArrayData = {};
    for (let i = 0; i < row; i++) {
      initialArrayData[i] = Array(column).fill(0);
    }
    setArrayData(initialArrayData);
  }, [row, column]);


  const handleSubmit =async () => {

      if(totalLayout===false){
        setUpdateData(dataArray)
      }else{
        setData(prevData => [...prevData, dataArray]);
        setFormSubmit(false)
        setDetails((prevValue) => ({ ...prevValue, row: 0, column: 0 }))
        const ans = getCount();
        setCount(ans);
      }
  };

  const getCount = () => {
    let total = 0;
    dataArray.map((value, i) => {
      let v = JSON.stringify(value);
      let x = v.length - v.replaceAll("1", "").length
      total += x
      setNewData(total);
    })
    return total;
  }

  function handleResult(data) {
    return data;
  }

  useEffect(() => {
    function handle(e) {
      if (e.target.className === "product-popup-parent") {
        setPopup(false)
      }
    }
    window.addEventListener("click", handle)
    return () => window.removeEventListener("click", handle)
  }, [])

  return (
    <div className="layout-page">
      {dataArray.map((row, rowIndex) => (
        <div key={rowIndex} className="row">
          {row.map((_, colIndex) => (
            <Cell
              key={colIndex}
              updateValue={updateValue}
              rowIndex={rowIndex}
              colIndex={colIndex}
            />
          ))}
        </div>
      ))}
      <div className="submit-btn-container">
        <button className="submit-btn" onClick={handleSubmit}>
         {data.length + 1 >= totalLayout ? "Submit Layout":"Add Layout"}
        </button>
      </div>
      {
        popup &&
        <div className='product-popup-parent'>
          <div className='product-popup'>
            <LayoutPopup count={count} setPopup={setPopup} setSubmit={setSubmit} />
          </div>
        </div>
      }
    </div>
  );
};
