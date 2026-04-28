import axios from "axios";
import { use, useState } from "react";
import { Link, useLocation, useNavigate } from "react-router-dom";
import LandingNavbar from "../Landing-page/landing-navbar"

function Login() {
    const [username, setUsername] = useState(undefined)
    const [password, setPassword] = useState(undefined)
    const [token, setToken] = useState(undefined)
    const [errMsg, setErrMsg] = useState(undefined)
    const navigate = useNavigate()
    const location = useLocation()

    const loginApi = "http://localhost:8080/api/auth/login"
    const detailsApi = "http://localhost:8080/api/auth/appUser-details"

    const processLogin = async (e) => {
        e.preventDefault()

        try {
            let encodedString = window.btoa(username + ":" + password)

            const config = {
                headers: {
                    "Authorization ": "Basic " + encodedString
                }
            }
            const response = await axios.get(loginApi, config)
            setToken(response.data.token)
            //console.log(response.data.token)
            localStorage.setItem("token", response.data.token)

            const detailsConfig = {
                headers: {
                    "Authorization": "Bearer " + response.data.token
                }
            }
            const detailsResponse = await axios.get(detailsApi, detailsConfig)

            const redirectTo = location.state?.redirectTo;
            const bookingData = location.state?.bookingData;

            switch (detailsResponse.data.role) {
                case "USER":
                    if (redirectTo) {
                        navigate(redirectTo, {
                            state: bookingData
                        });
                    } else {
                        navigate("/user-dashboard");
                    }
                    break;
                case "OWNER":
                    navigate("/owner-dashboard")
                    break;
                case "ADMIN":
                    navigate("/admin-dashboard")
                    break;
            }
        }
        catch (err) {
            setErrMsg("Invalid Credentials")
        }
    }

    return (
        <div className="container-fluid">
            <LandingNavbar />

            <div className="container d-flex justify-content-center align-items-center" style={{ height: "40rem" }}>

                <div className="card shadow-sm border-0 p-4" style={{ width: "22rem", borderRadius: "0.75rem" }}>

                    <h4 className="text-center text-primary mb-4 fw-bold">Login</h4>

                    <form onSubmit={(e) => processLogin(e)}>
                        {
                            errMsg == undefined ? "" :
                                <div className="alert alert-danger mt-4">
                                    {errMsg}
                                </div>
                        }
                        <div className="mb-3">
                            <label className="form-label small">Username</label>
                            <input type="text" className="form-control" placeholder="Enter username" required="required"
                                onChange={(e) => setUsername(e.target.value)} />
                        </div>

                        <div className="mb-3">
                            <label className="form-label small">Password</label>
                            <input type="password" className="form-control" placeholder="Enter password" required="required"
                                onChange={(e) => setPassword(e.target.value)} />
                        </div>

                        <div className="d-grid">
                            <button type="submit" className="btn btn-primary">Login</button>
                        </div>

                        <div className="text-center mt-3">
                            <small>
                                Don't have an account?{" "}
                                <Link to="/sign-up" className="text-primary fw-bold">
                                    Sign up
                                </Link>
                            </small>
                        </div>

                    </form>

                </div>

            </div>
        </div>
    )
}

export default Login;