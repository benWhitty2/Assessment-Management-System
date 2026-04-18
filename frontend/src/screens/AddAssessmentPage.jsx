import React, { useState, useEffect } from "react";
import "./LoginPage.css";
import { useNavigate, useLocation } from "react-router-dom";
import axios from "axios";

export const AddAssessmentPage = ({ client }) => {
    const navigate = useNavigate();
    const location = useLocation();
    const moduleId = location.state?.moduleId;

    console.log("AddAssessmentPage loaded.");
    console.log("Location object:", location);
    console.log("Location State:", location.state);
    console.log("Extracted Module ID:", moduleId);

    // Common State (All types of assessments must have these)
    const [users, setUsers] = useState([]);
    const [type, setType] = useState("Coursework");
    const [name, setName] = useState("");
    const [setter, setSetter] = useState(null);
    const [checker, setChecker] = useState(null);
    const [releasedToStudents, setReleasedToStudents] = useState(false);

    // Coursework State
    const [deadline, setDeadline] = useState("");
    const [specification, setSpecification] = useState("");

    // Exam/Test State
    const [duration, setDuration] = useState("");
    const [startTime, setStartTime] = useState("");
    const [ongoing, setOngoing] = useState(false);

    // Test State
    const [autograded, setAutograded] = useState(false);

    // Upon URL request, this will fetch users
    useEffect(() => {
        const fetchUsers = async () => {
            try {
                const usersData = await client.getUsers();
                console.log("API RETURNED:", usersData);
                console.log("Is it an array?", Array.isArray(usersData));
                setUsers(usersData);
                if (usersData && usersData.length > 0) {
                    setSetter(usersData[0]);
                    setChecker(usersData[0]);
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

    // Converts String to User Object
    const handleUserChange = (e, setFunction) => {
        const userId = parseInt(e.target.value);
        const selectedUser = users.find(u => u.id === userId);
        setFunction(selectedUser);
    };

    const submitHandler = async () => {
        if (!setter || !checker) {
            alert("Please select a setter and a checker.");
            return;
        }

        const basePayload = {
            name,
            setter: { id: setter.id },
            checker: { id: checker.id },
            releasedToStudents
        };

        let payload = {};

        if (type === "Coursework") {
            payload = { ...basePayload, deadline: deadline ? new Date(deadline).toISOString() : null, specification };
        } else if (type === "Exam") {
            payload = { ...basePayload, duration: duration ? new Date(duration).toISOString() : null, startTime: startTime ? new Date(startTime).toISOString() : null, ongoing };
        } else if (type === "Test") {
            payload = { ...basePayload, duration: duration ? new Date(duration).toISOString() : null, startTime: startTime ? new Date(startTime).toISOString() : null, ongoing, autograded };
        }

        try {
            let createdAssessment;
            if (type === "Coursework") {
                createdAssessment = await client.createCoursework(payload);
            } else if (type === "Exam") {
                createdAssessment = await client.createExam(payload);
            } else if (type === "Test") {
                createdAssessment = await client.createTest(payload);
            };

            alert(`${type} added successfully!`);

            console.log("Server Response for creating Assessment:", createdAssessment);

            const newId = createdAssessment.id;

            if (!newId) {
                throw new Error("ID was not returned from the server");
            }

            // Link Assessment to Module using AssessmentModule
            let createdAssessmentModule;
            const moduleIdInt = parseInt(moduleId, 10);
            const assessmentIdInt = parseInt(newId);
            console.log("THE ASSESSMENT ID IS ", newId);
            console.log("THE MODULE ID IS ", moduleId);

            if (!moduleId || isNaN(moduleIdInt)) {
                alert("Error: Module ID is missing. Please navigate from the Module page.");
                return;
            }

            if (!newId || isNaN(assessmentIdInt)) {
                alert("Error: Assessment ID is missing.");
                return;
            }

            if (type === "Coursework") {
                createdAssessmentModule = await client.createAssessmentModule({
                    moduleId: moduleIdInt,
                    assessmentId: assessmentIdInt,
                    stage: "CREATED",
                });
            } else if (type === "Exam") {
                createdAssessmentModule = await client.createAssessmentModule({
                    moduleId: moduleIdInt,
                    assessmentId: assessmentIdInt,
                    stage: "CREATED",
                });
            } else if (type === "Test") {
                createdAssessmentModule = await client.createAssessmentModule({
                    moduleId: moduleIdInt,
                    assessmentId: assessmentIdInt,
                    stage: "CREATED",
                });
            }

            navigate(`/module/${moduleId}`);

            console.log("Server Response for creating AssessmentModule:", createdAssessmentModule);

            alert(`${type} link added successfully!`);

        } catch (error) {
            console.error(`Error adding ${type}:`, error);
            alert(`An error occurred while creating ${type}.`);
        }

    };

    return (
        <main className="login-page-container">
            <div className="login-card" style={{ maxWidth: "600px", width: "100%" }}>
                <h1 className="login-title">Add Assessment</h1>

                <div className="form-group">
                    <label className="form-label">Type</label>
                    <select className="form-input" value={type} onChange={(e) => setType(e.target.value)}>
                        <option value="Coursework">Coursework</option>
                        <option value="Exam">Exam</option>
                        <option value="Test">Test</option>
                    </select>
                </div>

                <div className="form-group">
                    <label className="form-label">Name</label>
                    <input
                        type="text"
                        className="form-input"
                        placeholder="Assessment Name"
                        value={name}
                        onChange={(e) => setName(e.target.value)}
                    />
                </div>

                <div className="form-group">
                    <label className="form-label">Setter</label>
                    <select className="form-input" value={setter?.id || ""} onChange={(e) => handleUserChange(e, setSetter)}>
                        {Array.isArray(users) && users.map(user => (
                            <option key={user.id} value={user.id}>{user.username} ({user.role})</option>
                        ))}
                    </select>
                </div>

                <div className="form-group">
                    <label className="form-label">Checker</label>
                    <select className="form-input" value={checker?.id || ""} onChange={(e) => handleUserChange(e, setChecker)}>
                        {Array.isArray(users) && users.map(user => (
                            <option key={user.id} value={user.id}>{user.username} ({user.role})</option>
                        ))}
                    </select>
                </div>

                <div className="form-group" style={{ flexDirection: "row", alignItems: "center", gap: "10px" }}>
                    <label className="form-label" style={{ marginBottom: 0 }}>Released to Students</label>
                    <input
                        type="checkbox"
                        checked={releasedToStudents}
                        onChange={(e) => setReleasedToStudents(e.target.checked)}
                    />
                </div>

                {/* Coursework Specific Fields */}
                {type === "Coursework" && (
                    <>
                        <div className="form-group">
                            <label className="form-label">Deadline</label>
                            <input
                                type="datetime-local"
                                className="form-input"
                                value={deadline}
                                onChange={(e) => setDeadline(e.target.value)}
                            />
                        </div>
                        <div className="form-group">
                            <label className="form-label">Specification</label>
                            <textarea
                                className="form-input"
                                placeholder="Enter specification..."
                                value={specification}
                                onChange={(e) => setSpecification(e.target.value)}
                                rows={4}
                            />
                        </div>
                    </>
                )}

                {/* Exam & Test Specific Fields */}
                {(type === "Exam" || type === "Test") && (
                    <>
                        <div className="form-group">
                            <label className="form-label">Start Time</label>
                            <input
                                type="datetime-local"
                                className="form-input"
                                value={startTime}
                                onChange={(e) => setStartTime(e.target.value)}
                            />
                        </div>
                        <div className="form-group">
                            <label className="form-label">Duration (End Time)</label>
                            <input
                                type="datetime-local"
                                className="form-input"
                                value={duration}
                                onChange={(e) => setDuration(e.target.value)}
                            />
                        </div>
                        <div className="form-group" style={{ flexDirection: "row", alignItems: "center", gap: "10px" }}>
                            <label className="form-label" style={{ marginBottom: 0 }}>Ongoing</label>
                            <input
                                type="checkbox"
                                checked={ongoing}
                                onChange={(e) => setOngoing(e.target.checked)}
                            />
                        </div>
                    </>
                )}

                {/* Test Specific Fields */}
                {type === "Test" && (
                    <div className="form-group" style={{ flexDirection: "row", alignItems: "center", gap: "10px" }}>
                        <label className="form-label" style={{ marginBottom: 0 }}>Autograded</label>
                        <input
                            type="checkbox"
                            checked={autograded}
                            onChange={(e) => setAutograded(e.target.checked)}
                        />
                    </div>
                )}

                <button className="login-btn" onClick={submitHandler}>Create Assessment</button>
            </div>
        </main>
    );
};

export default AddAssessmentPage;