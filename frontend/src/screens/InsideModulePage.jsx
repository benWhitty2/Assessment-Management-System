import React, { useEffect, useState } from "react";
import { Header } from "../components/Header.jsx";
import { ButtonCompartment } from "../components/ButtonCompartment.jsx";
import { ModuleBar } from "../components/ModuleBar.jsx";
import { AssessmentCard } from "../components/AssessmentCard.jsx";
import { useParams, useNavigate } from "react-router-dom";
import "./InsideModulePage.css";

export const InsideModulePage = ({ client, user, onLogout }) => {
    const { id } = useParams();
    const navigate = useNavigate();
    const [module, setModule] = useState(null);

    useEffect(() => {
        if (client && id) {
            client.getModule(id).then(data => {
                console.log("Module data received:", data);
                setModule(data);
            }).catch(err => console.error("Error fetching module:", err));
        }
    }, [client, id]);


    if (!module) return <div>Loading...</div>;

    return (
        <>
            <Header user={user} onLogout={onLogout} />


            {
                (user?.role === "0" || user?.role === "1") ? (
                    <ButtonCompartment
                        pageName="inside-a-module-assessment"
                        moduleId={id}
                    />
                ) : (
                    <ButtonCompartment
                        pageName="progress"
                        moduleId={id}
                    />
                )
            }

            {module && (
                <ModuleBar
                    moduleCode={module.id}
                    moduleName={module.name}
                />
            )}

            <div style={{ margin: "20px" }}>
                <h1>Assessments</h1>
            </div>

            <div className="inside-page-container">

                {/* The Vertical Stack of Assessment Cards */}
                <div className="assessment-list-container">
                    {module.assessments && module.assessments.map((assessmentModule) => (
                        <AssessmentCard
                            key={assessmentModule.id}
                            id={assessmentModule.id}
                            assessment={assessmentModule.assessment}
                            currentStage={assessmentModule.stage}
                        />
                    ))}
                </div>
            </div>
        </>
    );
};

export default InsideModulePage;