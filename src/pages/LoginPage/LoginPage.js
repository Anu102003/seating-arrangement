import React, { useState } from 'react'
import "./loginPage.scss"
import { Login } from './Login'
import { Register } from './Register'

export const LoginPage = ({setAuthenticate}) => {
    const [isLogin, setLogin] = useState(true)
    const onChangeLogin = () => {
        isLogin ? setLogin(false) : setLogin(true)
    }


    return (
        <>

            <div className="login-grid-card">

                {/* 1 */}
                <div className='grid-child'>
                </div>

                {/* 2 */}
                <div className='login-grid-content'>

                    {/* left container */}
                    <div className='login-left-container'>

                        {
                            isLogin ?
                                <img src="https://img.freepik.com/free-vector/login-concept-illustration_114360-757.jpg?w=740&t=st=1710869663~exp=1710870263~hmac=9a67358eb0eb768533e557950359e0a305b553c2f9c18f751fd9d7feb927cb04" />
                                :
                                <img src="https://img.freepik.com/free-vector/mobile-login-concept-illustration_114360-83.jpg?w=740&t=st=1710870019~exp=1710870619~hmac=555908d847eb14855da5252e4c1abc846826459998ac243daff3e7888da5c0a2" />
                        }
                    </div>

                    <div className='login-right-wrapper'>
                        {/* right container */}
                        <div className='login-right-container'>
                            <div className='login-signin'>
                                {
                                    isLogin ? <Login setAuthenticate={setAuthenticate}/> : <Register setLogin={setLogin}/>
                                }
                            </div>
                        </div>


                        <div className='login-sign-option'>
                            {
                                isLogin ?
                                    <div className='login-sign'>
                                        Don't have an account ? <span onClick={onChangeLogin} className='sign-text' >Sign up</span>
                                    </div> :
                                    <div className='login-sign'>
                                        Have an account ? <span onClick={onChangeLogin} className='sign-text'>Sign in</span>
                                    </div>
                            }
                        </div>

                    </div>
                </div>

                {/* 3 */}
                <div className='grid-child'>
                </div>

            </div>
        </>
    )
}

