import React, { useEffect, useState } from 'react'
import "./AllLayout.scss"
import { LayoutPopup } from '../../assets/components/LayoutPopup/LayoutPopup'
import { useNavigate } from 'react-router-dom'

export const AllLayouts = ({ allLayouts }) => {

  const [popup, setPopup] = useState(false)
  const [optionSelected, setOptionSelected] = useState("")
  const [submit, setSubmit] = useState(false)
  const navigate = useNavigate()
  const [layoutSelected, setLayoutSeleted] = useState()
  const [selectedOption, setSelectedOption] = useState([])
  const handleLayoutSelected = (layout) => {
    setPopup(true)
    setOptionSelected("")
    setSubmit(false)
    setLayoutSeleted(layout)
  }
  const handleFilter = (options) => {
    if (!selectedOption.includes(options)) {
      setSelectedOption((prev) => [
        ...prev, options
      ])
    } else {
      setSelectedOption(prev => {
        return prev.filter(selectedOption => selectedOption !== options)
      }
      )
    }
  }
  console.log(selectedOption)
  let filterData;
  if (selectedOption.includes("Old") && selectedOption.includes("New")) {
    filterData = allLayouts?.companyLayout?.map((item) => item)
  } else if (selectedOption.includes("New")) {
    filterData = allLayouts?.companyLayout?.filter(item => !item.changed)
  } else if (selectedOption.includes("Old")) {
    filterData = allLayouts?.companyLayout?.filter(item => item.changed)
  } else {
    filterData = allLayouts?.companyLayout?.map((item) => item)
  }
  console.log(filterData)
  useEffect(() => {
    if (submit && optionSelected !== "") {
      if (optionSelected === "Seating") {
        navigate("/seating", {
          state: {
            data: layoutSelected.companyLayout,
            layoutId: layoutSelected.layoutId,
            availableSpaces: layoutSelected.totalSpace
          }
        });
      }
      if (optionSelected === "Allocation") {
        navigate("/allocation", {
          state: {
            data: layoutSelected.companyLayout,
            layoutId: layoutSelected.layoutId,
            availableSpaces: layoutSelected.totalSpace
          }
        });
      }
    }
  })

  useEffect(() => {
    function handle(e) {
      if (e.target.className === "allocation-seating-popup-parent") {
        setPopup(false)
        setLayoutSeleted()
        setOptionSelected("")
        setSubmit(false)
      }
    }
    window.addEventListener("click", handle)
    return () => window.removeEventListener("click", handle)
  }, [])

  return (
    <div className='all-layout-page'>
      <table className="MyTable">
        <div className='filter-options'>
          <p className={selectedOption.includes("Old")?'option-btn':'option-btns'} onClick={() => handleFilter("Old")}>Old Layouts</p>
          <p className={selectedOption.includes("New")?'option-btn':'option-btns'} onClick={() => handleFilter("New")}>New Layouts</p>
        </div>
        <tbody>
          {filterData?.map((layout, index) => (
            <div className='layout-list' onClick={() => { handleLayoutSelected(layout) }}>
              <div>
                <p className='heading'>Layout {index + 1}</p>
                {
                  selectedOption.length===0 &&
                 <h5 style={{ color: layout.changed && "#FF6767" ,paddingTop:"1rem"}}>{!layout.changed ? "( New Layout )" : "( Old Layout )"}</h5> 
                }
              </div>
              <div className='grid-wrapper'>
                {layout?.companyLayout?.map((row, i) => {
                  return (
                    <tr key={i} >
                      {row?.map((value, j) => {
                        return (
                          <>
                            <td
                              key={j}
                              className="grid-box"
                              style={{
                                backgroundColor:
                                  value === 1 ? "#F4BD36" : "#eeecf0",
                                color:
                                  value === 1 ? "#F4BD36" : "#eeecf0",
                              }}>
                              {value}
                            </td>
                          </>
                        );
                      })}
                    </tr>
                  );
                })
                }
              </div>
            </div>
          ))}

        </tbody>
      </table>
      {
        popup &&
        <div className='allocation-seating-popup-parent'>
          <div className='allocation-seating-popup'>
            <LayoutPopup layoutOptions={true} setPopup={setPopup} optionSelected={optionSelected} setOptionSelected={setOptionSelected} setSubmit={setSubmit} />
          </div>
        </div>
      }
    </div>
  )
}
