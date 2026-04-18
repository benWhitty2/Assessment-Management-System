import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import "./LoginPage.css";

export const DemoteUser = ({ client, user, onLogout }) => {
    const navigate = useNavigate();
    const [errors, setErrors] = useState({});
    const [examOfficer, setexamOfficer] = useState(null);

    const [users, setUsers] = useState([]);

    // Converts String to User Object
    const handleUserChange = (e, setFunction) => {
        const userId = parseInt(e.target.value);
        const selectedUser = users.find(u => u.id === userId);
        setFunction(selectedUser);
    };

    // Upon URL request, this will fetch users
    useEffect(() => {
        const fetchUsers = async () => {
            try {
                const usersData = await client.getUsers();
                console.log("API RETURNED:", usersData);
                console.log("Is it an array?", Array.isArray(usersData));
                setUsers(usersData);
                if (usersData && usersData.length > 0) {
                    // Default to the first user that matches the filter (e.g., Role 0 for Exam Officer)
                    const firstEligibleUser = usersData.find(u => u.role === "0" || u.role === 0);
                    setexamOfficer(firstEligibleUser || null);
                }
            } catch (error) {
                console.error("Error fetching users:", error);
                if (!client) {
                    console.error("Client object is undefined. Make sure it is passed as a prop.");
                }
                alert("Failed to fetch users. Check console for details.");
            }
        };
        fetchUsers();
    }, []);

    const submitHandler = async () => {
        if (!examOfficer) {
            alert("Please select a user to demote.");
            return;
        }

        // Prohibit self-demotion
        if (user.id === examOfficer.id) {
            alert("Self-demotion is prohibited.");
            return;
        }

        if (user.id !== examOfficer.id) {
            try {
                const response = await client.updateUser(examOfficer.id, {
                    username: examOfficer.username,
                    email: examOfficer.email,
                    role: "2"
                });
                console.log("User successfully demoted:", response);
                alert("User demoted successfully!");
                navigate("/dashboard");

            } catch (error) {
                console.error("Demote user error:", error);
                alert("An error occurred while demoting the user.");
            }
        }

    };

    return (
        <main className="login-page-container">
            <div className="login-card" style={{ maxWidth: "500px" }}>
                <h1 className="login-title">Demote an Exam Officer</h1>

                <div className="form-group">
                    <label className="form-label">Select an Exam Officer</label>
                    <select className="form-input" value={examOfficer?.id || ""} onChange={(e) => handleUserChange(e, setexamOfficer)}>
                        {Array.isArray(users) && users
                            .filter(user => user.role === "0")
                            .map(user => (
                                <option key={user.id} value={user.id}>{user.username} ({user.role})</option>
                            ))}
                    </select>
                </div>

                <button className="login-btn" onClick={submitHandler}>Demote User</button>
            </div>
        </main>
    );
};

export default DemoteUser;