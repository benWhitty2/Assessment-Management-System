import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import "./LoginPage.css";

export const PromoteUser = ({ client }) => {
    const navigate = useNavigate();
    const [errors, setErrors] = useState({});
    const [academicStaff, setAcademicStaff] = useState(null);

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
                    // Default to the first user that matches the filter (e.g., Role 2)
                    const firstEligibleUser = usersData.find(u => u.role === "2" || u.role === 2);
                    setAcademicStaff(firstEligibleUser || null);
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
        if (!academicStaff) {
            alert("Please select a user to promote.");
            return;
        }

        try {
            const response = await client.updateUser(academicStaff.id, {
                username: academicStaff.username,
                email: academicStaff.email,
                role: "0"
            });
            console.log("User successfully promoted:", response);
            alert("User promoted successfully!");
            navigate("/dashboard");

        } catch (error) {
            console.error("Promote user error:", error);
            alert("An error occurred while promoting the user.");
        }
    };

    return (
        <main className="login-page-container">
            <div className="login-card" style={{ maxWidth: "500px" }}>
                <h1 className="login-title">Promote an Academic Staff</h1>

                <div className="form-group">
                    <label className="form-label">Select an Academic Staff</label>
                    <select className="form-input" value={academicStaff?.id || ""} onChange={(e) => handleUserChange(e, setAcademicStaff)}>
                        {Array.isArray(users) && users
                            .filter(user => user.role === "2")
                            .map(user => (
                                <option key={user.id} value={user.id}>{user.username} ({user.role})</option>
                            ))}
                    </select>
                </div>

                <button className="login-btn" onClick={submitHandler}>Promote User</button>
            </div>
        </main>
    );
};

export default PromoteUser;