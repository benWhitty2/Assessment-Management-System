import React, { useRef, useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import "./LoginPage.css";

// 'setToken' and 'setUser' are passed from App.jsx
export const LoginPage = ({ setToken, setUser }) => {

    const usernameRef = useRef(null);
    const passwordRef = useRef(null);

    const navigate = useNavigate();

    const submitHandler = (e) => {
        e.preventDefault();
        const username = usernameRef.current.value;
        const password = passwordRef.current.value;

        axios.post("http://localhost:8080/auth/login", {}, { // Empty body
            auth: {
                username: username, // Axios will encode this into a Basic Auth header
                password: password
            }
        })
            .then(({ data }) => {
                console.log("Login Success! Token:", data.token);
                setToken(data.token);
                setUser(data.user);
                navigate("/dashboard");
            })
            .catch(err => {
                console.error(err);
                window.alert("Login failed. Please check your credentials.");
            });
    };

    return (
        <main className="login-page-container">
            <div className="login-card">
                <h1 className="login-title">Assessment <br /> Management</h1>

                <form onSubmit={submitHandler}>
                    <div className="form-group">
                        <label htmlFor="username" className="form-label">Username</label>
                        <input
                            id="username"
                            type="text"
                            className="form-input"
                            placeholder="Enter username"
                            ref={usernameRef}
                        />
                    </div>

                    <div className="form-group">
                        <label htmlFor="password" className="form-label">Password</label>
                        <input
                            id="password"
                            type="password"
                            className="form-input"
                            placeholder="Enter password"
                            ref={passwordRef}
                        />
                    </div>

                    <button type="submit" className="login-btn">Log In</button>
                </form>
            </div>
        </main>
    );
};

export default LoginPage;