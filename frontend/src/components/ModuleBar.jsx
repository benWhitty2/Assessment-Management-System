import React from "react";
import "./ModuleBar.css";

export const ModuleBar = ({
    moduleCode,
    moduleName,
    currentState,
}) => {
    return (
        <div className="module-bar">

            {/* Top Left: Module Identity */}
            <div className="bar-identity">
                <h3 className="bar-code">{moduleCode}</h3>
                <span className="bar-name">{moduleName}</span>
            </div>

            {/* Bottom: Assessment Details (Type, State, Checker, Setter) */}
            <div className="bar-details-grid">
                {/* Column 2: Current State */}
                {currentState && (
                    <div className="bar-detail-item">
                        <span className="bar-label">Current State</span>
                        <span className="bar-value">{currentState}</span>
                    </div>
                )}
            </div>
        </div>
    );
};