import React from 'react'
import "./singleAllocation.scss"
import { useLocation } from 'react-router-dom'

import { AllocationLayout } from '../../assets/components/AllocationLayout/AllocationLayout'
export const SingleAllocation = () => {

    const location = useLocation()
    const allocation = location.state.allocation
    const layout = location.state.layout

    return (
        <div className='single-allocation-page'>

            <h2 className='h-1'>Allocation Layout</h2>
            {allocation &&
                allocation.allocationDataList.map((items) => {
                    return (
                        <AllocationLayout items={items} layout={layout} allocation={allocation}/>
                    )
                })}
        </div>
    )
}
