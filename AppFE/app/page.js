"use client"
import { BrowserRouter, Route, Routes } from "react-router-dom";
import Header from "./components/header";
import Footer from "./components/Footer";
//pages
import LandingPage from "./components/LandingPage";
import Assignment from "./components/Assignment";
import Assignment2 from "./components/Assignment2";
import AssignmentList from "./pages/assignment_list/page";
import SignUpPage from "./components/SignUpPage"

import { CustomerProvider } from "./components/CustomerContext";
import ProtectedRoute from "./components/ProtectedRoutes";

export default function Home() {
  return (
    <main className="flex flex-col items-center bg-gradient-to-t from-yellow-100 to-white px-4 lg:px-8">
      <BrowserRouter>
          <CustomerProvider>
            <Header />
            <Routes>
              <Route path="/" element={<LandingPage />}/>
              <Route path="/Assignment1" element={<ProtectedRoute element={<Assignment />}/>}/>
              <Route path="/Assignment2" element={<ProtectedRoute element={<Assignment2 />}/>}/>
              <Route path="/AssignmentList" element={<ProtectedRoute element={<AssignmentList />}/>}/>
              <Route path="/SignUpPage" element={<SignUpPage />}/>
            </Routes>
          </CustomerProvider>
      </BrowserRouter>
      
    </main>
  );
}