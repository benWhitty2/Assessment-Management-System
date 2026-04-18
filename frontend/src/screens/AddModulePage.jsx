import React, { useState, useEffect } from "react";
import "./LoginPage.css";
import { useNavigate } from "react-router-dom";
import axios from "axios";

// Using css same as Login Page

export const AddModulePage = ({ client, user }) => {
    const navigate = useNavigate();

    const [id, setId] = useState("");
    const [name, setName] = useState("");
    const [studyYear, setStudyYear] = useState(1);
    const [users, setUsers] = useState([]);
    const [selectedUser, setSelectedUser] = useState(null);

    // Fetch users on mount
    useEffect(() => {
        const fetchUsers = async () => {
            try {
                if (!client) {
                    console.error("Client object is undefined. Make sure it is passed as a prop.");
                    return;
                }
                const usersData = await client.getUsers();
                setUsers(usersData);
                if (usersData && usersData.length > 0) {
                    setSelectedUser(usersData[0]);
                }
            } catch (error) {
                console.error("Error fetching users:", error);
                alert("Failed to fetch users. Check console for details.");
            }
        };
        fetchUsers();
    }, [client]);

    const handleUserChange = (e) => {
        const userId = parseInt(e.target.value);
        const found = users.find(u => u.id === userId);
        setSelectedUser(found);
    };

    const submitHandler = async () => {
        if (!id || !name || !selectedUser) {
            alert("Please enter a Module Code, Name, and select a Module Lead.");
            return;
        }

        try {
            const response = await client.createModule({
                id: parseInt(id),
                name: name,
                studyYear: studyYear
            });

            const addThisUserToModule = await client.createModuleStaff({
                module: { id: response.id },
                user: { id: user.id },
                moduleRole: "S"
            });
            console.log("Module successfully added:", response);
            console.log("User successfully added to module:", addThisUserToModule);
            alert("Module added successfully!");

            const response2 = await client.createModuleStaff({
                user: { id: selectedUser.id },
                module: { id: parseInt(id) },
                moduleRole: "L" // L for Module Lead
            });
            console.log("User added to module as lead:", response2);
            alert("Module Lead added successfully!");

            navigate("/dashboard");

        } catch (error) {
            console.error("Add module error:", error);
            alert("An error occurred while creating the module.");
        }
    };

    return (
        <main className="login-page-container">
            <div className="login-card" style={{ maxWidth: "500px" }}>
                <h1 className="login-title">Add Module</h1>

                <div className="form-group">
                    <label className="form-label">Module Code (ID)</label>
                    <input
                        type="number"
                        className="form-input"
                        placeholder="e.g. 101"
                        value={id}
                        onChange={(e) => setId(e.target.value)}
                    />
                </div>

                <div className="form-group">
                    <label className="form-label">Module Name</label>
                    <input
                        type="text"
                        className="form-input"
                        placeholder="e.g. Security"
                        value={name}
                        onChange={(e) => setName(e.target.value)}
                    />
                </div>

                <div className="form-group">
                    <label className="form-label">Study Year</label>
                    <input
                        type="number"
                        className="form-input"
                        placeholder="e.g. 1"
                        value={studyYear}
                        onChange={(e) => setStudyYear(Number(e.target.value))}
                    />
                </div>

                <div className="form-group">
                    <label className="form-label">Select a Module Lead</label>
                    <select className="form-input" value={selectedUser?.id || ""} onChange={handleUserChange}>
                        {Array.isArray(users) && users.map(user => (
                            <option key={user.id} value={user.id}>{user.username} ({user.role})</option>
                        ))}
                    </select>
                </div>

                <button className="login-btn" onClick={submitHandler}>Create Module</button>
            </div>
        </main>
    );
};

export default AddModulePage;