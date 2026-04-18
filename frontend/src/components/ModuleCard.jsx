import React from "react";
import "./ModuleCard.css";
import { useNavigate } from "react-router-dom";

export const ModuleCard = ({ module_code, module_name, studyYear }) => {
    const navigate = useNavigate();

    const onModuleClick = () => {
        navigate(`/module/${module_code}`); // Navigate to the module page, TODO match with the module id ${id}
    };

    return (
        <div className="module-card" onClick={onModuleClick}>
            <div className="card-header-section">
                <h2 className="module-id">{module_code}</h2>
                <h3 className="module-name">{module_name}</h3>
            </div>

            <div className="card-details">

                <div className="detail-row">
                    <span className="detail-label">Study Year:</span>
                    <span className="detail-value">{studyYear}</span>
                </div>

                {/* <div className="detail-row">
                    <span className="detail-label">Module Lead:</span>
                    <span className="detail-value">{lead}</span>
                </div>

                <div className="detail-row">
                    <span className="detail-label">Moderator:</span>
                    <span className="detail-value">{moderator}</span>
                </div>

                <div className="detail-row">
                    <span className="detail-label">Assessments:</span>
                    <span className="detail-value">{assessmentCount}</span>
                </div> */}

            </div>
        </div>
    );
};