import { faCircleExclamation } from '@fortawesome/free-solid-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import React from 'react'
import "./errorPopup.scss"

export const ErrorPopup = ({ error,inCorrectFile }) => {
    return (
        <div className='error-wrapper'>
            <FontAwesomeIcon icon={faCircleExclamation} color='#cc3131' size='5x' style={{ marginTop: "4rem" }} />
            {
                error.outOfSpace &&
                <p className='error-txt'>
                    Given space is more than Available Space
                </p>
            }
            {
                error.file &&
                <p className='error-txt'>
                    Please Add team list details
                </p>
            }
            {
                error.addTeam &&
                <p className='error-txt'>
                    Please verify to submit
                </p>
            }
            {
                error.fileDataIncorrect &&
                <>
                    <p className='error-txt'>
                        The data in the file does not match with Available Spaces
                    </p>
                    <p className='error-txt'>
                        Please provide correct data
                    </p>
                </>
            }
            {
                inCorrectFile &&
                <>
                    <p className='error-txt'>
                        File format is incorrect
                    </p>
                    <p className='error-txt'>
                        Please provide xlsx file
                    </p>
                </>
            }
        </div>
    )
}









{/* !layout?( file!==undefined || file!=="")?"Add team list":"Please verify to submit":  */ }
