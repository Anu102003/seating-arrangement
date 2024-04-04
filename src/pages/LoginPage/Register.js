import React, { useState } from 'react'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import {  faCircleInfo, faEnvelope, faEye, faEyeSlash, faKey } from '@fortawesome/free-solid-svg-icons'
import { registerApi } from '../../actions/ApiCall'

export const Register = ({ setLogin }) => {
    const [registerValue, setregisterValue] = useState({
        email: "",
        companyName: "",
        password: ""
    })
    const [registerError, setregisterError] = useState({
        emailError: "",
        companyNameError: "",
        passwordError: "",
        validateError: ""
    })
    const [validregister, setValidregister] = useState({
        emailValid: false,
        companyNameValid: false,
        passwordValid: false
    });
    const [registerLoading, setregisterLoading] = useState(false);
    const [registerEyeVisible, setregisterEyeVisible] = useState(false)


    //validating sign in value
    const mobileEmailChange = (e) => {
        const val = e.target.value;
        if (val === "") {
            setregisterError({ ...registerError, emailError: "Please enter a value" })
            setValidregister({ ...validregister, emailValid: false })
        }
        //email valid
        else if ((/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(val))) {
            setregisterValue({ ...registerValue, email: e.target.value })
            setregisterError({ ...registerError, emailError: "" })
            setValidregister({ ...validregister, emailValid: true })
        }
        else {
            setregisterError({ ...registerError, emailError: "Please enter a valid email" })
            setValidregister({ ...validregister, emailValid: false })
        }
    }


    //validating full name value
    const companyNameChange = (e) => {
        const companyName = e.target.value;
        if (companyName === "") {
            setregisterError({ ...registerError, companyNameError: "Please enter a value" })
            setValidregister({ ...validregister, companyNameValid: false })
        }
        //username valid
        else if (!(/^[A-Z][a-z]*( [A-Z][a-z]*)*$/.test(companyName))) {
            if (!/^[A-Z]/.test(companyName)) {
                setregisterError({ ...registerError, companyNameError: "Company name must start with a capital letter" })
                setValidregister({ ...validregister, companyNameValid: false })
            } else if (/\d/.test(companyName)) {
                setregisterError({ ...registerError, companyNameError: "Company name cannot contain numbers" })
                setValidregister({ ...validregister, companyNameValid: false })
            } else if (/[^a-zA-Z\s]/.test(companyName)) {
                setregisterError({ ...registerError, companyNameError: "Company name cannot contain special characters" })
                setValidregister({ ...validregister, companyNameValid: false })
            }
            else {
                setregisterValue({ ...registerValue, companyName: e.target.value })
                setregisterError({ ...registerError, companyNameError: "" })
                setValidregister({ ...validregister, companyNameValid: true })
            }
        }
        else {
            setregisterValue({ ...registerValue, companyName: e.target.value })
            setregisterError({ ...registerError, companyNameError: "" })
            setValidregister({ ...validregister, companyNameValid: true })
        }
    }



    //validating password 
    const registerPasswordChange = (e) => {

        const pwd = e.target.value;
        if (pwd === "") {
            setregisterError({ ...registerError, passwordError: "Please enter a password" })
            setValidregister({ ...validregister, passwordValid: false })
        } else if (/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*()_+[\]{};':"\\|,.<>\/?]).{8,15}$/.test(pwd) && pwd.length >= 8 && pwd.length <= 15) {
            setregisterValue({ ...registerValue, password: e.target.value })
            setregisterError({ ...registerError, passwordError: "" })
            setValidregister({ ...validregister, passwordValid: true })
        } else if (pwd.length > 15) {
            setregisterError({ ...registerError, passwordError: "Password must be 15 character" })
            setValidregister({ ...validregister, passwordValid: false })
        } else {
            setregisterError({ ...registerError, passwordError: "Password must contain at least one uppercase letter, one lowercase letter, one special character  and one digit" })
            setValidregister({ ...validregister, passwordValid: false })
        }
    }

    //sign up validate
    const validateregister = async (e) => {
        e.preventDefault();
        if (validregister.emailValid === true && validregister.companyNameValid === true
            && validregister.passwordValid === true) {
            setregisterError({ ...registerError, validateError: "" })
            const response = await registerApi(registerValue)
            try {
                if (response.message === "Register Successfully") {
                    // console.log(response.message)
                    setLogin(true)
                }
                else if (response.response.status !== 200) {
                    setregisterError({ ...registerError, validateError: response.response.data.message })
                    console.log(response.response.data.message);
                }
            } catch (err) {
                console.log("Error in register")
            }
            setregisterLoading(true)
            setTimeout(() => {
                setregisterLoading(false);
            }, 2000);
        } else {
            setregisterError({ ...registerError, validateError: "Please enter correct data" })
            setregisterLoading(false)
        }
    }

    return (

        <>
            <div className='headings'>
                Register
            </div>
            {/* Input field of register value */}
            <div className='fields'>
                <div className='input-field-wrapper'>
                    <FontAwesomeIcon icon={faEnvelope}className='icon'  />
                    <input className='login-text'
                        defaultValue={setregisterValue.email === "" ? "" : setregisterValue.email}
                        onChange={mobileEmailChange}
                        type='text'
                        placeholder='Email address'
                        autocomplete="off" />
                </div>
            </div>
            <p className='login-error'>{registerError.emailError}</p>

            {/* Input field of Full Name value */}
            <div className='fields'>
                <div className='input-field-wrapper'>
                <FontAwesomeIcon icon={faCircleInfo} className='icon'/>
                    <input className='login-text'
                        defaultValue={setregisterValue.companyName === "" ? "" : setregisterValue.companyName}
                        onChange={companyNameChange}
                        type='text'
                        placeholder='Company Name'
                        autocomplete="off" />
                </div>
            </div>

            <p className='login-error'>{registerError.companyNameError}</p>


            {/* Input field of Password value */}
            <div className='fields'>
                <div className='input-field-wrapper'>
                <FontAwesomeIcon icon={faKey} className='icon' />
                    <input className='login-text'
                        defaultValue={setregisterValue.password === "" ? "" : setregisterValue.password}
                        onChange={registerPasswordChange}
                        type={registerEyeVisible ? "text" : "password"}
                        placeholder='Password'
                        autocomplete="off" />
                    <div className='eye-icon' onClick={() => setregisterEyeVisible(!registerEyeVisible)}>
                        {
                            registerEyeVisible ?
                                <FontAwesomeIcon icon={faEye} /> :
                                <FontAwesomeIcon icon={faEyeSlash} />
                        }
                    </div>
                </div>
            </div>
            <p className='login-error'>{registerError.passwordError}</p>

            {/* Sign Up Button */}
            <button className='login-button' onClick={validateregister}>
                <div>
                    Sign Up
                </div>
            </button>
            {
                registerLoading ?
                    <div className='login-loading'>
                        {/* <img src={loginpage.loading} width="35px" /> */}
                    </div> :
                    <></>
            }
            <p className='login-error'>{registerError.validateError}</p>
        </>
    )
}