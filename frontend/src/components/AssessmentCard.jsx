import React from "react";
import "./AssessmentCard.css";
import { useNavigate } from "react-router-dom";

export const AssessmentCard = ({ id, assessment, currentStage }) => {
    const navigate = useNavigate();

    const onAssessmentCardClick = () => {
        console.log("Assessment Card Clicked");
        
        // Check if assessment is an exam
        if (assessment && assessment.type === "Exam") {
            navigate(`/progress-exam/${id}`);
        } else {
            navigate(`/progress/${id}`);
        }
    };

    const type = assessment ? assessment.name : "Unknown Assessment";

    return (
        <div className="assessment-card" onClick={onAssessmentCardClick}>
            {/* 1. The Type of Assessment */}
            <h3 className="assessment-type">{type}</h3>

            {/* 3. Current Stage */}
            <div className="assessment-stage-container">
                <span className="stage-label">Current Stage:</span>
                <span className="stage-value">{currentStage}</span>
            </div>
        </div>
    );
};