import React, { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { Header } from "../components/Header.jsx";
import { ModuleCard } from "../components/ModuleCard.jsx";
import { ButtonCompartment } from "../components/ButtonCompartment.jsx";
import "./ListOfModulesPage.css";

export const ListOfModulesPage = ({ client, modules, user, onLogout }) => {

    useEffect(() => {
        if (client && user?.id) {
            // Check if user is Exam Officer (0) or Teaching Support (1)
            if (user.role == "0" || user.role == "1") {
                client.getModules();
            } else {
                client.getModules(user.id);
            }
        }
    }, [client, user]);

    return (
        <>
            <Header user={user} onLogout={onLogout} />

            {user?.role == "0" ? (
                <ButtonCompartment pageName="exam-officer-dashboard" />
            ) : user?.role == "1" ? (
                <ButtonCompartment pageName="teaching-support-team-dashboard" />
            ) : null}

            <div className="dashboard-container">
                <p className="page-subtitle">Welcome to your dashboard! Here are your assigned modules.</p>

                <div className="modules-grid">
                    {console.log("Modules data:", modules)}
                    {Array.isArray(modules) && modules.map((module) => (
                        <ModuleCard
                            key={module.id}
                            module_code={module.id}
                            module_name={module.name}
                            studyYear={module.studyYear}
                        />
                    ))}
                </div>
            </div>
        </>
    );
};

export default ListOfModulesPage;