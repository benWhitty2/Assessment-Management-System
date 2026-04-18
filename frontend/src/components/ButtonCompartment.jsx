import React from "react";
import "./ButtonCompartment.css";
import { useNavigate } from "react-router-dom";

export const ButtonCompartment = ({ pageName, moduleId }) => {
    const navigate = useNavigate();

    // Logic for each button
    const onAddUser = () => {
        console.log("Add User Clicked");
        navigate("/adduser");
    };

    const onAddModule = () => {
        console.log("Add Module Clicked");
        navigate("/addmodule");
    };

    const onAddAssessment = () => {
        console.log("Add Assessment Clicked. Module ID:", moduleId);
        navigate("/addassessment", { state: { moduleId: moduleId } });
    };

    const onAddUserToModule = () => {
        console.log("Add User to Module Clicked. Module ID:", moduleId);
        navigate("/addusertomodule", { state: { moduleId: moduleId } });
    };

    const onBack = () => {
        console.log("Go back to List of Modules");
        navigate("/dashboard");
    };

    const onBackToAssessment = () => {
        console.log("Go back to Dashboard");
        navigate("/dashboard");
    };

    const onPromoteUser = () => {
        console.log("Promote User Clicked");
        navigate("/promoteuser");
    };

    const onDemoteUser = () => {
        console.log("Demote User Clicked");
        navigate("/demoteuser");
    };

    const onSwitchView = () => {
        console.log("Switch View Clicked");
    };



    if (pageName === "exam-officer-dashboard") {
        return (
            <div className="button-compartment dashboard-mode">
                <div className="dashboard-actions">
                    <button className="action-btn btn-primary" onClick={onAddModule}>
                        + Add Module
                    </button>
                    <button className="action-btn btn-secondary" onClick={onAddUser}>
                        + Add User
                    </button>
                    <button className="action-btn btn-secondary" onClick={onPromoteUser}>
                        + Promote Academic Staff to Exam Officer
                    </button>
                    <button className="action-btn btn-secondary" onClick={onDemoteUser}>
                        - Demote Exam Officer to Academic Staff
                    </button>
                    <button className="action-btn btn-secondary" onClick={onSwitchView}>
                        = Switch to Teaching Support Team View
                    </button>
                </div>
            </div>
        );
    }
    if (pageName === "teaching-support-team-dashboard") {
        return (
            <div className="button-compartment dashboard-mode">
                <div className="dashboard-actions">
                    <button className="action-btn btn-primary" onClick={onAddModule}>
                        + Add Module
                    </button>
                    <button className="action-btn btn-secondary" onClick={onAddUser}>
                        + Add User
                    </button>
                </div>
            </div>
        );
    }

    if (pageName === "inside-a-module") {
        return (
            <div className="button-compartment detail-mode">
                <button className="action-btn btn-back" onClick={onBack}>
                    ← Go Back to Dashboard
                </button>
            </div>
        );
    }

    if (pageName === "inside-a-module-assessment") {
        return (
            <>
                <div className="button-compartment detail-mode">
                    <button className="action-btn btn-back" onClick={onBack}>
                        ← Go Back to Dashboard
                    </button>
                    <div className="dashboard-actions">
                        <button className="action-btn btn-primary" onClick={onAddAssessment}>
                            + Add Assessment
                        </button>
                        <button className="action-btn btn-primary" onClick={onAddUserToModule}>
                            + Add  a User to this Module
                        </button>
                    </div>
                </div>
            </>
        );
    }

    if (pageName === "progress") {
        return (
            <>
                <div className="button-compartment detail-mode">
                    <button className="action-btn btn-back" onClick={onBackToAssessment}>
                        ← Go Back to Dashboard
                    </button>
                </div>
            </>
        );
    }

    return null;
};