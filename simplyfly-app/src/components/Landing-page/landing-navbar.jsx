import { useNavigate } from "react-router-dom"

function LandingNavbar() {
    const navigate = useNavigate()

    return (
        <div className="container-fluid">
            <nav className="navbar navbar-expand-lg navbar-dark bg-primary shadow-sm">
                <div className="container-fluid">
                    <a className="navbar-brand fw-bold" href="#">✈️ SimplyFly</a>

                    <div className="d-flex">
                        <button className="btn btn-light btn-sm" onClick={() => navigate("/login")}>Login</button>
                    </div>
                </div>
            </nav>

            <div className="row mt-5 text-center">

                <div className="col-lg-12">
                    <h1 className="fw-bold text-primary">Book Flights Easily</h1>
                    <p className="text-muted">Fast • Secure • Hassle-free booking</p>
                </div>

            </div>
        </div>
    )
}

export default LandingNavbar