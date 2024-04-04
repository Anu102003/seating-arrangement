import React from 'react'
import "./pageNotFound.scss"
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faCircleExclamation } from '@fortawesome/free-solid-svg-icons'
export const PageNotFound = () => {
    return (
        <div className='page-not-found-page'>
            <FontAwesomeIcon icon={faCircleExclamation} style={{color: "#ce223b",}} size="10x" />
            <h1 className='heading'>
                Page Not Found
            </h1>
        </div>
    )
}
