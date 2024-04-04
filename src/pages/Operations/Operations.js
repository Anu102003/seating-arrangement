import React, { useContext, useEffect, useState } from 'react'
import "./Operations.scss"
import { useNavigate } from 'react-router-dom'
import { deleteLayoutApi, getAlllayoutApi } from '../../actions/ApiCall'
import { UpdatePopup } from '../../assets/components/UpdatePopup/UpdatePopup'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faClose } from '@fortawesome/free-solid-svg-icons'
import { LayoutPopup } from '../../assets/components/LayoutPopup/LayoutPopup'

export const Operations = () => {
    const companyName = localStorage.getItem('companyName');
    const navigate = useNavigate()
    const [allLayouts, setLayouts] = useState()
    const [popup, setPopup] = useState(false)
    const [deleteSelected, setDeleteSelected] = useState(false)
    const [deleteSubmit, setDeleteSubmit] = useState(false);
    const [layoutSeleted, setLayoutSeleted] = useState()
    const [selectedOption, setSelectedOption] = useState([])
    const handleLayoutSelected = (layout) => {
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
        handleLayoutSelected();
    }, []);
    const handleAdd = () => {
        navigate("/layoutform", { state: { totalLayout: false, } })
    }
    useEffect(() => {
        function handle(e) {
            if (e.target.className === "update-popup-parent") {
                setPopup(false)
                document.body.style.overflow = "unset"
            }
            if (e.target.className === "delete-popup-parent") {
                setDeleteSelected(false)
                document.body.style.overflow = "unset"
            }
        }
        window.addEventListener("click", handle)
        return () => window.removeEventListener("click", handle)
    }, [])
    useEffect(() => {
        async function getAllLayouts() {
            try {
                const res = await getAlllayoutApi(companyName)
                setLayouts(res.data)
                if (res.message === "Company not found") {
                } else {
                }
            } catch (err) {
                console.log(err)
            }
        }
        getAllLayouts()
    }, [companyName, deleteSelected, popup])

    // useEffect(() => {
    //     console.log(layoutSeleted?.layoutId)
    // }, [layoutSeleted])

    useEffect(() => {
        async function handleDeleteSubmit() {
            console.log(deleteSubmit, layoutSeleted?.layoutId)
            if (deleteSubmit) {
                try {
                    console.log("first", layoutSeleted?.layoutId)
                    const res = await deleteLayoutApi(
                        layoutSeleted.layoutId,
                        companyName,
                    )
                    if (res.message === "updates done") {
                        navigate("/operations")
                    }
                    setDeleteSelected(false)
                    setDeleteSubmit(false)
                    document.body.style.overflow = "unset"
                } catch (err) {
                    console.log("Error in deleting", err)
                }
            }
        }
        handleDeleteSubmit()
    }, [deleteSubmit])
    console.log(deleteSubmit)
    return (
        <div className='operations-page'>
            <div className='add-btn-wrapper'>
                <button className='add-btn' onClick={handleAdd}>
                    +  Add
                </button>
            </div>
            <div className='operation-layout-page'>
                <table className="MyTable">
                    <div className='filter-options'>
                        <p className={selectedOption.includes("Old") ? 'option-btn' : 'option-btns'} onClick={() => handleFilter("Old")}>Old Layouts</p>
                        <p className={selectedOption.includes("New") ? 'option-btn' : 'option-btns'} onClick={() => handleFilter("New")}>New Layouts</p>
                    </div>
                    <tbody>
                        {filterData?.map((layout, index) => (
                            <div className='operation-layout-list' onClick={() => handleLayoutSelected(layout)}>
                                <div className='grid-wrapper' onClick={() => { setPopup(true); document.body.style.overflow = "hidden" }}>
                                    <div>
                                        <p className='heading'>Layout {index + 1}</p>
                                        <h5 style={{ color: layout.changed && "#FF6767" }}>{!layout.changed ? "( New Layout )" : "( Old Layout )"}</h5>
                                    </div>
                                    <div >
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
                                                                            value === 1 ? "#3FA9F5" : "#eeecf0",
                                                                        color:
                                                                            value === 1 ? "#3FA9F5" : "#eeecf0",
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
                                <div className='btn-wrapper'>
                                    <button className='update-btn' onClick={() => { setPopup(true); document.body.style.overflow = "hidden" }}>Update</button>
                                    <button className='delete-btn' onClick={() => { setDeleteSelected(true); document.body.style.overflow = "hidden" }}>Delete</button>
                                </div>
                            </div>
                        ))}

                    </tbody>
                </table>
                {
                    popup &&
                    <div className='update-popup-parent'>
                        <div className='update-popup'>
                            <div className='close-icon' onClick={() => { setPopup(false); document.body.style.overflow = "unset" }}>
                                <FontAwesomeIcon icon={faClose} size='2xl' />
                            </div>
                            <UpdatePopup layoutSeleted={layoutSeleted} setPopup={setPopup} />

                        </div>
                    </div>
                }
                {
                    deleteSelected &&
                    <div className='delete-popup-parent'>
                        <div className='delete-popup'>
                            <LayoutPopup setPopup={setDeleteSelected} setSubmit={setDeleteSubmit} deletePopup={true} />
                        </div>
                    </div>
                }

            </div>
        </div>
    )
}
