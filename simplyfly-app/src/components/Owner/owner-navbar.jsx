import { Link, useNavigate } from "react-router-dom";

function OwnerNavbar() {
    const navigate = useNavigate();

    const logout = () => {
        localStorage.clear();
        navigate("/");
    };

    return (
        <nav className="navbar navbar-expand-lg navbar-dark bg-primary shadow-sm">
            <div className="container-fluid">
                
                {/* Logo */}
                <Link className="navbar-brand fw-bold" to="/owner-dashboard">
                    ✈️ SimplyFly
                </Link>

                {/* Menu */}
                <ul className="navbar-nav me-auto">
                    <li className="nav-item">
                        <Link className="nav-link text-white" to="/owner/flights">
                            Flights
                        </Link>
                    </li>

                    <li className="nav-item">
                        <Link className="nav-link text-white" to="/owner/bookings">
                            Bookings
                        </Link>
                    </li>
                </ul>

                {/* Logout */}
                <div className="d-flex">
                    <button className="btn btn-light btn-sm" onClick={logout}>
                        Logout
                    </button>
                </div>

            </div>
        </nav>
    );
}

export default OwnerNavbar;