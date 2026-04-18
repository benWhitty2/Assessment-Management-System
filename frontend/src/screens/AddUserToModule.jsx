import React, { useState, useEffect } from "react";
import "./LoginPage.css";
import { useNavigate, useLocation } from "react-router-dom";

export const AddUserToModule = ({ client }) => {
    const navigate = useNavigate();
    const location = useLocation();
    const moduleId = location.state?.moduleId;

    console.log("AddUserToModule page loaded.");
    console.log("Extracted Module ID:", moduleId);

    const [users, setUsers] = useState([]);
    const [role, setRole] = useState("S"); // Default to 'S' (Academic staff)
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
        if (!selectedUser) {
            alert("Please select a user.");
            return;
        }
        if (!moduleId) {
            alert("Module ID is missing. Please navigate from the Module page.");
            return;
        }

        const payload = {
            user: { id: selectedUser.id },
            module: { id: parseInt(moduleId) },
            moduleRole: role
        };

        try {
            console.log("Sending payload:", payload);
            const response = await client.createModuleStaff(payload);
            console.log("Server Response:", response);

            alert("User added to module successfully!");
            navigate(`/module/${moduleId}`);

        } catch (error) {
            console.error("Error adding user to module:", error);
            alert("An error occurred while adding the user.");
        }
    };

    return (
        <main className="login-page-container">
            <div className="login-card" style={{ maxWidth: "600px", width: "100%" }}>
                <h1 className="login-title">Add User to Module</h1>

                <div className="form-group">
                    <label className="form-label">Module Role</label>
                    <select className="form-input" value={role} onChange={(e) => setRole(e.target.value)}>
                        <option value="S">Academic staff</option>
                        <option value="M">Moderator</option>
                    </select>
                </div>

                <div className="form-group">
                    <label className="form-label">Select a User</label>
                    <select className="form-input" value={selectedUser?.id || ""} onChange={handleUserChange}>
                        {Array.isArray(users) && users.map(user => (
                            <option key={user.id} value={user.id}>{user.username} ({user.role})</option>
                        ))}
                    </select>
                </div>

                <button className="login-btn" onClick={submitHandler}>Add User</button>
            </div>
        </main>
    );
};

export default AddUserToModule;