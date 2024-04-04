import React, {  useEffect, useState } from 'react'
import { AllLayouts } from '../AllLayouts/AllLayouts'
import LayoutRegister from '../LayoutRegister/LayoutRegister'
import {  getAlllayoutApi } from '../../actions/ApiCall'

export const Home = ({companyNotFound,setCompanyNotFound}) => {

  const companyName = localStorage.getItem('companyName');

  const [allLayouts,setLayouts]=useState()
  useEffect(() => {
    async function getAllLayouts() {
      try {
        const res = await getAlllayoutApi(companyName)
        // console.log(res)
        setLayouts(res.data)
        if(res.message==="Company not found"){
          setCompanyNotFound(true)
        }else{
          setCompanyNotFound(false)
        }
      } catch (err) {
        console.log(err)
      }
    }
    getAllLayouts()
  }, [companyName])
  return (
    <>{
      companyNotFound ?
        <LayoutRegister />
       : 
       <AllLayouts allLayouts={allLayouts}/>
    }
    </>
  )
}


