import { useNavigate } from "react-router-dom";
import "./Header.css";

export const Header = ({ user, onLogout }) => {
    const navigate = useNavigate();

    const handleLogout = () => {
        console.log("Logging out...");
        if (onLogout) {
            onLogout();
        }
        navigate("/login");
    };
    let displayRole;
    if (user.role === "0") {
        displayRole = "Exam Officer";
    } else if (user.role === "1") {
        displayRole = "Teaching Support Team";
    } else if (user.role === "2") {
        displayRole = "Academic Staff Member";
    } else if (user.role === "3") {
        displayRole = "External Examiner";
    }
    return (
        <header className="app-header">
            {/* <div className="header-left">
                <img src="https://via.placeholder.com/150x50?text=Logo" alt="App Logo" className="logo" />
            </div> */}

            <div className="header-right">
                <div className="user-info">
                    <span className="user-name">{user?.username || "Guest"}</span>
                    <span className="user-role">{displayRole || "Visitor"}</span>
                </div>

                <button className="logout-btn" onClick={handleLogout}>
                    Log Out
                </button>
            </div>
        </header>
    );
};