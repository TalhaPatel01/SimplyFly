import { Link, useNavigate } from "react-router-dom";

function UserNavbar() {
    const navigate = useNavigate();

    const logout = ()=>{
        localStorage.clear()
        navigate("/")
    }

    return (
        <nav className="navbar navbar-expand-lg navbar-dark bg-primary shadow-sm">
            <div className="container-fluid">
                <Link className="navbar-brand fw-bold" to="/">
                    ✈️ SimplyFly
                </Link>

                <ul className="navbar-nav me-auto">

                    <li className="nav-item">
                        <Link className="nav-link text-white" to="/my-trips">
                            My Trips
                        </Link>
                    </li>

                    <li className="nav-item">
                        <Link className="nav-link text-white" to="/support">
                            Support
                        </Link>
                    </li>

                </ul>

                <div className="d-flex">
                    <button className="btn btn-light btn-sm" onClick={logout}>
                        Logout
                    </button>
                </div>

            </div>
        </nav>
    );
}

export default UserNavbar;