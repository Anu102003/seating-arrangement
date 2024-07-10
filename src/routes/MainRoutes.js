import React, { useEffect, useState } from "react";
import { BrowserRouter, Navigate, Route, Routes } from "react-router-dom";
import Seating from "../pages/Seating/Seating";
import { LoginPage } from "../pages/LoginPage/LoginPage";
import LayoutForm from "../pages/LayoutForm/LayoutForm";
import LayoutRegister from "../pages/LayoutRegister/LayoutRegister";
import { Home } from "../pages/Home/Home";
import { Operations } from "../pages/Operations/Operations";
import NavBar from "../pages/NavBar";
import { Allocation } from "../pages/Allocation/Allocation";
import { SingleAllocation } from "../pages/SingleAllocation/SingleAllocation";
import { PageNotFound } from "../pages/PageNotFound/PageNotFound";

const MainRoutes = () => {
  const [companyNotFound, setCompanyNotFound] = useState(false)
  const [authenticate, setAuthenticate] = useState(false);
  const accessToken = localStorage.getItem('accessToken');
  useEffect(() => {
    function handleAuthenticate() {
      console.log(accessToken)
      if (accessToken === "" || accessToken === null) {
        setAuthenticate(false)
        console.log(authenticate)
      } else {
        setAuthenticate(true)
        console.log(authenticate)
      }
    }
    handleAuthenticate()
  }, [accessToken, authenticate])
  return (
    <BrowserRouter>
      {
        authenticate &&
        <NavBar companyNotFound={companyNotFound} setAuthenticate={setAuthenticate} authenticate={authenticate} />
      }
      <Routes>
        <Route path="/" element={authenticate ? <Navigate to="/home" /> : <LoginPage setAuthenticate={setAuthenticate} />} />
        <Route path="/seating" element={authenticate ? <Seating /> : <Navigate to="/" />} />
        <Route path="/register" element={authenticate ? <LayoutRegister /> : <Navigate to="/" />} />
        <Route path="/layoutform" element={authenticate ? <LayoutForm /> : <Navigate to="/" />} />
        <Route path="/allocation" element={authenticate ? <Allocation /> : <Navigate to="/" />} />
        <Route path="/allocationitem" element={authenticate ? <SingleAllocation /> : <Navigate to="/" />} />
        <Route path="/operations" element={authenticate ? <Operations /> : <Navigate to="/" />} />
        <Route path="/home" element={authenticate ? <Home companyNotFound={companyNotFound} setCompanyNotFound={setCompanyNotFound} /> : <Navigate to="/" />} />
        <Route path="*" element={<PageNotFound />} />
      </Routes>
    </BrowserRouter>
  );
};

export default MainRoutes;
