import React, { useState, useEffect } from "react";
import { Header } from "../components/Header.jsx";
import { ButtonCompartment } from "../components/ButtonCompartment.jsx";
import { ModuleBar } from "../components/ModuleBar.jsx";
import "./ProgressPage.css";
import { useParams } from "react-router-dom";

const STAGES = ["CREATED", "RELEASED", "SUBMITTED", "MARKED", "MODERATED", "COMPLETED"];

export const ProgressPage = ({ client, user, onLogout }) => {
    const { id } = useParams();
    const [moduleInfo, setModuleInfo] = useState(null);
    const [currentStage, setCurrentStage] = useState("CREATED");
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchData = async () => {
            if (!client) return;
            try {
                // Fetch assessment module details
                const data = await client.getAssessmentModule(id);
                console.log("Assessment Module Received:", {
                    assessmentId: data.assessment?.id,
                    id: data.id,
                    moduleId: data.module?.id,
                    stage: data.stage
                });

                setModuleInfo({
                    code: data.module?.id || "N/A",
                    name: data.module?.name || "Unknown Module",
                    type: data.assessment?.name || "Unknown Assessment",
                    state: data.stage || "Unknown",
                });
                setCurrentStage(data.stage || "CREATED");
                setLoading(false);
            } catch (error) {
                console.error("Error fetching data:", error);
                // Fallback mock data
                setModuleInfo({
                    code: "Error",
                    name: "Error",
                    type: "Error",
                    state: "Error",
                });
                setCurrentStage("CREATED");
                setLoading(false);
            }
        };

        if (id) {
            fetchData();
        }
    }, [id, client]);

    const handleStageUpdate = async (newStage) => {
        if (!client || !moduleInfo) return;
        
        // Check if user can only progress one step forward or backward
        const currentIndex = STAGES.indexOf(currentStage);
        const newIndex = STAGES.indexOf(newStage);
        
        if (newIndex !== currentIndex + 1 && newIndex !== currentIndex - 1) {
            alert("You can only progress to the previous or next stage.");
            return;
        }
        
        console.log("Updating stage to:", newStage);
        try {
            // Optimistic update
            setCurrentStage(newStage);

            // Construct minimal payload to avoid deserialization issues with nested objects
            const updatePayload = {
                id: id,
                stage: newStage,
                assessment: null,
                module: null
            };
            console.log("Sending update payload:", updatePayload);

            const updatedResponse = await client.updateAssessmentModule(id, updatePayload);
            console.log("Update response:", updatedResponse);

            // Refresh data
            const refreshedData = await client.getAssessmentModule(id);
            console.log("Refreshed data:", refreshedData);
            setCurrentStage(refreshedData.stage);

        } catch (error) {
            console.error("Error updating stage:", error);
        }
    };

    if (loading) return <div>Loading...</div>;

    return (
        <>
            <Header client={client} user={user} onLogout={onLogout} />

            <ButtonCompartment
                pageName="progress"
            />

            {/* The Module Bar showing summary info */}
            {moduleInfo && (
                <ModuleBar
                    moduleCode={moduleInfo.type}
                    currentState={currentStage}
                />
            )}

            <div style={{ margin: "20px" }}>
                <h1>Assessment Progress</h1>
                <p>Click a button to update the current stage.</p>
            </div>

            <div className="progress-page-container" style={{ display: 'flex', flexDirection: 'column', gap: '10px', alignItems: 'center' }}>
                {STAGES.map((stage, index) => {
                    const currentIndex = STAGES.indexOf(currentStage);
                    const isCurrentStage = index === currentIndex;
                    const isNextStage = index === currentIndex + 1;
                    const isPreviousStage = index === currentIndex - 1;
                    const isClickable = isNextStage || isPreviousStage;

                    return (
                        <button
                            key={stage}
                            onClick={() => {
                                if (isClickable) {
                                    handleStageUpdate(stage);
                                }
                            }}
                            style={{
                                padding: "15px 30px",
                                fontSize: "16px",
                                backgroundColor: isCurrentStage ? "#4CAF50" : isNextStage ? "#2196F3" : "#ddd", // Green for current, Blue for next, Grey otherwise
                                color: isCurrentStage || isClickable ? "white" : "gray",
                                border: "1px solid #ccc",
                                borderRadius: "5px",
                                cursor: isClickable ? "pointer" : "not-allowed",
                                width: "200px",
                                fontWeight: isCurrentStage ? "bold" : "normal",
                                opacity: isCurrentStage || isClickable ? 1 : 0.5,
                                pointerEvents: isClickable ? "auto" : "none"
                            }}
                        >
                            {stage}
                        </button>
                    );
                })}
            </div>
        </>
    );
};

export default ProgressPage;