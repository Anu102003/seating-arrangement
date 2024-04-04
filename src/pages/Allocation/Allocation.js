import React, { useEffect, useState } from 'react'
import "./allocation.scss"
import { useLocation, useNavigate } from 'react-router-dom';
import { getAllAllocationApi } from '../../actions/ApiCall';

export const Allocation = () => {
    const location = useLocation();
    const navigate = useNavigate();
    const data = location.state.data;
    const layoutId = location.state.layoutId;
    const availableSpaces = location.state.availableSpaces;
    const [allocationList, setAllocationList] = useState()

    const handleNavigate = () => {
        navigate("/seating", {
            state: {
                data: data,
                layoutId: layoutId,
                availableSpaces: availableSpaces
            }
        });
    }
    const handleSingleAllocation = (selectedAllocation) => {
        navigate("/allocationitem", {
            state: {
                allocation: selectedAllocation,
                layout: data
            }
        })
    }
    
    useEffect(() => {
        async function getAllAllocation() {
            try {
                const res = await getAllAllocationApi(layoutId)
                setAllocationList(res.data)
            } catch (err) {
                console.log(err)
            }
        }
        getAllAllocation()
    }, [layoutId])

    return (
        <div className='allocation-page'>
            <h2 className='h-1'>
                Total Allocation : {allocationList?.length}
            </h2>
            {
                allocationList?.length > 0 ?
                    <>
                        {
                            allocationList?.map((data, index) => (
                                <div className='heading-1 list' onClick={() => handleSingleAllocation(data)}>Allocation {index + 1}</div>
                            ))
                        }
                    </> :
                    <>
                        <h3 className='heading'>No Allocations has been done yet</h3>
                        <div className='navigate-btn-wrapper'>
                            <button className='navigate-btn' onClick={handleNavigate}>Click here to see allocation</button>
                        </div>
                    </>
            }
        </div>
    )
}
