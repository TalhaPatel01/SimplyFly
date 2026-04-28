import axios from "axios"
import { Link } from "react-router-dom"
import { useState } from "react"

function SignUp() {
    const [name, setName] = useState(undefined)
    const [email, setEmail] = useState(undefined)
    const [phoneNo, setPhoneNo] = useState(undefined)
    const [username, setUsername] = useState(undefined)
    const [password, setPassword] = useState(undefined)
    const [errMsg, setErrMsg] = useState(undefined)
    const [successMsg, setSuccessMsg] = useState(undefined)

    const signUpApi = "http://localhost:8080/api/user/sign-up"

    const processSignUp = async (e) => {
        e.preventDefault()

        if(!name || !email || !phoneNo || !username || !password){
            setErrMsg("Please fill all details")
        }

        try {
            await axios.post(signUpApi, {
                "name": name,
                "email": email,
                "phone_no": phoneNo,
                "username": username,
                "password": password
            })
            setSuccessMsg("Sign up complete, login now")
            setErrMsg(undefined)
        }
        catch (err) {
            setErrMsg(err.message)
            setSuccessMsg(undefined)
        }
    }

    return (
        <div className="container py-5 d-flex justify-content-center">

            <div className="card shadow-sm border-0 p-4" style={{ width: "22rem", borderRadius: "1rem" }}>

                <h4 className="text-center text-primary mb-4 fw-bold">Sign Up</h4>

                <form onSubmit={(e) => processSignUp(e)}>
                    {
                        errMsg == undefined ? "" :
                            <div className="alert alert-danger mt-4">
                                {errMsg}
                            </div>
                    }
                    {
                        successMsg == undefined ? "" :
                            <div className="alert alert-primary mt-4">
                                {successMsg}
                            </div>
                    }
                    <div className="mb-3">
                        <label className="form-label small">Name</label>
                        <input type="text" className="form-control" placeholder="Enter name"
                            onChange={(e) => setName(e.target.value)} />
                    </div>

                    <div className="mb-3">
                        <label className="form-label small">Email</label>
                        <input type="text" className="form-control" placeholder="Enter email"
                            onChange={(e) => setEmail(e.target.value)} />
                    </div>

                    <div className="mb-3">
                        <label className="form-label small">Phone No</label>
                        <input type="text" className="form-control" placeholder="Enter Phone No"
                            onChange={(e) => setPhoneNo(e.target.value)} />
                    </div>

                    <div className="mb-3">
                        <label className="form-label small">Username</label>
                        <input type="text" className="form-control" placeholder="Enter username"
                            onChange={(e) => setUsername(e.target.value)} />
                    </div>

                    <div className="mb-3">
                        <label className="form-label small">Password</label>
                        <input type="password" className="form-control" placeholder="Enter password"
                            onChange={(e) => setPassword(e.target.value)} />
                    </div>

                    <div className="d-grid">
                        <button type="submit" className="btn btn-primary">Sign Up</button>
                    </div>

                    <div className="text-center mt-3">
                        <small>
                            Already have an account?{" "}
                            <Link to="/login" className="text-primary fw-bold">
                                Login
                            </Link>
                        </small>
                    </div>

                </form>

            </div>

        </div>
    )
}

export default SignUp