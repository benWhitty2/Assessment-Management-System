import React, { useState, useMemo } from "react";
import "./styles/custom.css";
import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import { LoginPage } from "./screens/LoginPage";
import { ListOfModulesPage } from "./screens/ListOfModulesPage.jsx";
import { InsideModulePage } from "./screens/InsideModulePage.jsx";
import { ProgressPage } from "./screens/ProgressPage.jsx";
import { ProgressPage as ProgressPageExam } from "./screens/ProgressPageExam.jsx";
import { AddUserPage } from "./screens/AddUserPage.jsx";
import { AddModulePage } from "./screens/AddModulePage.jsx";
import { AddAssessmentPage } from "./screens/AddAssessmentPage.jsx";
import { AddUserToModule } from "./screens/AddUserToModule.jsx";
import { PromoteUser } from "./screens/PromoteUser.jsx";
import { DemoteUser } from "./screens/DemoteUser.jsx";
import axios from "axios";

function App() {
    const [token, setToken] = useState("");
    const [user, setUser] = useState(null);
    const [modules, setModules] = useState([]);

    // The client "captures" the token variable here
    const client = useMemo(() => ({
        getUsers: () => axios.get("http://localhost:8080/users", {
            headers: { "Authorization": `Bearer ${token}` } // Token is used here
        }).then(({ data }) => data),

        createUser: (data) => axios.post("http://localhost:8080/auth/signup", data, {
            headers: { "Authorization": `Bearer ${token}` }
        }).then(({ data }) => data),

        getModules: (userId) => {
            let url = "http://localhost:8080/modules";
            if (userId) {
                url += `?userId=${userId}`;
            }
            return axios.get(url, {
                headers: { "Authorization": `Bearer ${token}` } // Token is used here
            }).then(({ data }) => setModules(data));
        },

        getModule: (id) => axios.get(`http://localhost:8080/modules/${id}`, {
            headers: { "Authorization": `Bearer ${token}` }
        }).then(({ data }) => data),

        createModule: (data) => axios.post("http://localhost:8080/modules", data, {
            headers: { "Authorization": `Bearer ${token}` }
        }).then(({ data }) => data),

        getModuleStaff: () => axios.get(`http://localhost:8080/modulestaff/${id}`, {
            headers: { "Authorization": `Bearer ${token}` }
        }).then(({ data }) => data),

        createModuleStaff: (data) => axios.post("http://localhost:8080/modulestaff", data, {
            headers: { "Authorization": `Bearer ${token}` }
        }).then(({ data }) => data),

        createCoursework: (data) => axios.post("http://localhost:8080/courseworks", data, {
            headers: { "Authorization": `Bearer ${token}` }
        }).then(({ data }) => data),

        createExam: (data) => axios.post("http://localhost:8080/exams", data, {
            headers: { "Authorization": `Bearer ${token}` }
        }).then(({ data }) => data),

        getExam: (id) => axios.get(`http://localhost:8080/exams/${id}`, {
            headers: { "Authorization": `Bearer ${token}` }
        }).then(({ data }) => data),

        updateExam: (id, data) => axios.put(`http://localhost:8080/exams/${id}`, data, {
            headers: { "Authorization": `Bearer ${token}` }
        }).then(({ data }) => data),

        createTest: (data) => axios.post("http://localhost:8080/tests", data, {
            headers: { "Authorization": `Bearer ${token}` }
        }).then(({ data }) => data),

        createAssessmentModule: (data) => axios.post("http://localhost:8080/assessment-modules", data, {
            headers: { "Authorization": `Bearer ${token}` }
        }).then(({ data }) => data),

        getAssessmentModule: (id) => axios.get(`http://localhost:8080/assessment-modules/${id}`, {
            headers: { "Authorization": `Bearer ${token}` }
        }).then(({ data }) => data),

        updateAssessmentModule: (id, data) => axios.put(`http://localhost:8080/assessment-modules/${id}`, data, {
            headers: { "Authorization": `Bearer ${token}` }
        }).then(({ data }) => data),

        updateUser: (id, data) => axios.put(`http://localhost:8080/users/${id}`, data, {
            headers: { "Authorization": `Bearer ${token}` }
        }).then(({ data }) => data)
    }), [token]);

    const handleLogout = () => {
        console.log("Logging out...");
        setToken("");
        setUser(null);
    };

    return (
        <BrowserRouter>
            <Routes>
                {/* After a successful login, we can get a token */}
                <Route
                    path="/login"
                    element={<LoginPage setToken={setToken} setUser={setUser} />}
                />

                <Route
                    path="/dashboard"
                    element={<ListOfModulesPage client={client} modules={modules} user={user} onLogout={handleLogout} />}
                />

                <Route path="/adduser" element={<AddUserPage client={client} user={user} />} />
                <Route path="/promoteuser" element={<PromoteUser client={client} user={user} />} />
                <Route path="/demoteuser" element={<DemoteUser client={client} user={user} onLogout={handleLogout} />} />
                <Route path="/addmodule" element={<AddModulePage client={client} user={user} />} />
                <Route path="/addassessment" element={<AddAssessmentPage client={client} user={user} />} />

                <Route path="/addusertomodule" element={<AddUserToModule client={client} user={user} />} />

                <Route path="/module/:id" element={<InsideModulePage client={client} user={user} onLogout={handleLogout} />} />

                <Route path="/progress/:id" element={<ProgressPage client={client} user={user} onLogout={handleLogout} />} />
                <Route path="/progress-exam/:id" element={<ProgressPageExam client={client} user={user} onLogout={handleLogout} />} />
                {/* Default path */}
                <Route path="*" element={<Navigate to="/login" />} />
            </Routes>
        </BrowserRouter>
    );
}

export default App;