import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import "./LoginPage.css";

export const AddUserPage = ({ client }) => {
    const navigate = useNavigate();

    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [email, setEmail] = useState("");
    const [role, setRole] = useState("Exam Officer");
    const [errors, setErrors] = useState({});

    // Converts role names to string numbers
    const getRoleNumber = (roleName) => {
        switch (roleName) {
            case "Exam Officer":
                return "0";
            case "Teaching Support Team":
                return "1";
            case "Academic Staff":
                return "2";
            case "External Examiner":
                return "3";
            default:
                return "0"; // Default to Admin
        }
    };

    const validateEmail = (email) => {
        if (!email) return 'Email is required';
        if (!email.includes('@')) return 'Email entered is not valid';
        return '';
    };

    const validatePassword = (password) => {
        if (!password) return 'Password is required';
        if (password.length < 8) return 'Password must be at least 8 characters long';
        if (!/[!@#$%^&*(),.?":{}|<>]/.test(password)) return 'Password must contain at least one special character';
        return '';
    };

    const validateForm = () => {
        const emailError = validateEmail(email);
        const passwordError = validatePassword(password);
        const usernameError = !username ? 'Username is required' : '';
        const roleError = !role ? 'Role is required' : '';

        const newErrors = {
            email: emailError,
            password: passwordError,
            username: usernameError,
            role: roleError
        };

        setErrors(newErrors);
        return !emailError && !passwordError && !usernameError && !roleError;
    };

    const submitHandler = async () => {
        if (!validateForm()) {
            return;
        }

        try {
            const roleNumber = getRoleNumber(role);
            const response = await client.createUser({
                username,
                password,
                email,
                role: roleNumber
            });
            console.log("User successfully added:", response);
            alert("User added successfully!");
            navigate("/dashboard"); // Redirect to modules or home

        } catch (error) {
            console.error("Add user error:", error);
            alert("An error occurred while creating the user.");
        }
    };

    return (
        <main className="login-page-container">
            <div className="login-card" style={{ maxWidth: "500px" }}>
                <h1 className="login-title">Add User</h1>

                <div className="form-group">
                    <label className="form-label">Username</label>
                    <input
                        type="text"
                        placeholder="Enter username"
                        value={username}
                        onChange={(e) => {
                            setUsername(e.target.value);
                            setErrors(prev => ({ ...prev, username: '' }));
                        }}
                        className={`form-input ${errors.username ? 'invalid' : ''}`}
                    />
                    {errors.username && <div className="error-message">{errors.username}</div>}
                </div>

                <div className="form-group">
                    <label className="form-label">Password</label>
                    <input
                        type="password"
                        placeholder="Enter password"
                        value={password}
                        onChange={(e) => {
                            setPassword(e.target.value);
                            setErrors(prev => ({ ...prev, password: '' }));
                        }}
                        className={`form-input ${errors.password ? 'invalid' : 'valid'}`}
                    />
                    {errors.password && <div className="error-message">{errors.password}</div>}
                </div>

                <div className="form-group">
                    <label className="form-label">Email</label>
                    <input
                        type="email"
                        placeholder="Enter email"
                        value={email}
                        onChange={(e) => {
                            setEmail(e.target.value);
                            setErrors(prev => ({ ...prev, email: '' }));
                        }}
                        className={`form-input ${errors.email ? 'invalid' : 'valid'}`}
                    />
                    {errors.email && <div className="error-message">{errors.email}</div>}
                </div>

                <div className="form-group">
                    <label className="form-label">Role</label>
                    <select
                        value={role}
                        onChange={(e) => {
                            setRole(e.target.value);
                            setErrors(prev => ({ ...prev, role: '' }));
                        }}
                        className={`form-input ${errors.role ? 'invalid' : ''}`}
                    >
                        <option value="">Select a role</option>
                        <option value="Academic Staff">Academic Staff</option>
                        <option value="Teaching Support Team">Teaching Support Team</option>
                        <option value="Exam Officer">Exam Officer</option>
                        <option value="External Examiner">External Examiner</option>
                    </select>
                    {errors.role && <div className="error-message">{errors.role}</div>}
                </div>

                <button className="login-btn" onClick={submitHandler}>Create User</button>
            </div>
        </main>
    );
};

export default AddUserPage;