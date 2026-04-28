import { useEffect, useState } from "react"
import axios from "axios"
import AdminNavbar from "./admin-navbar"

function OwnerList() {

    const [owners, setOwners] = useState([])
    const [page, setPage] = useState(0)
    const [totalPages, setTotalPages] = useState(0)

    const [showForm, setShowForm] = useState(false)

    const [form, setForm] = useState({
        airline_name: "",
        email: "",
        phone: "",
        username: "",
        password: ""
    })

    const size = 5

    useEffect(() => {
        fetchOwners()
    }, [page])

    const fetchOwners = async () => {
        try {
            const config = {
                headers: {
                    "Authorization": "Bearer " + localStorage.getItem("token")
                }
            }

            const res = await axios.get(
                `http://localhost:8080/api/owner/get-all?page=${page}&size=${size}`,
                config
            )

            setOwners(res.data.list)
            setTotalPages(res.data.totalPages)

        } catch (err) {
            console.log(err.message)
        }
    }

    const handleChange = (e) => {
        setForm({ ...form, [e.target.name]: e.target.value })
    }

    const addOwner = async () => {
        try {
            const config = {
                headers: {
                    "Authorization": "Bearer " + localStorage.getItem("token")
                }
            }

            await axios.post(
                "http://localhost:8080/api/owner/sign-up",
                form,
                config
            )

            // reset form
            setForm({
                airline_name: "",
                email: "",
                phone: "",
                username: "",
                password: ""
            })
            setShowForm(false)
            fetchOwners()

        } catch (err) {
            console.log(err.message)
        }
    }

    return (
        <div className="container-fluid bg-light min-vh-100">

            {/* Navbar */}
            <div className="row">
                <AdminNavbar />
            </div>

            <div className="container mt-4">

                {/* Header */}
                <div className="d-flex justify-content-between align-items-center mb-3">
                    <h3 className="fw-bold">🧑‍✈️ Flight Owners</h3>

                    <button
                        className="btn btn-primary"
                        onClick={() => setShowForm(!showForm)}
                    >
                        + Add Owner
                    </button>
                </div>

                {/* Add Owner Form */}
                {showForm && (
                    <div className="card p-3 mb-3 shadow-sm">
                        <div className="row g-2">

                            <div className="col">
                                <input
                                    className="form-control"
                                    placeholder="Airline Name"
                                    name="airline_name"
                                    value={form.airline_name}
                                    onChange={handleChange}
                                />
                            </div>

                            <div className="col">
                                <input
                                    className="form-control"
                                    placeholder="Email"
                                    name="email"
                                    value={form.email}
                                    onChange={handleChange}
                                />
                            </div>

                            <div className="col">
                                <input
                                    className="form-control"
                                    placeholder="Phone"
                                    name="phone"
                                    value={form.phone}
                                    onChange={handleChange}
                                />
                            </div>

                            <div className="col">
                                <input
                                    className="form-control"
                                    placeholder="Username"
                                    name="username"
                                    value={form.username}
                                    onChange={handleChange}
                                />
                            </div>

                            <div className="col">
                                <input
                                    type="password"
                                    className="form-control"
                                    placeholder="Password"
                                    name="password"
                                    value={form.password}
                                    onChange={handleChange}
                                />
                            </div>

                            <div className="col-auto">
                                <button className="btn btn-success" onClick={addOwner}>
                                    Save
                                </button>
                            </div>

                        </div>
                    </div>
                )}

                {/* Table */}
                <div className="card shadow-sm border-0 rounded-4">
                    <div className="card-body">

                        <table className="table table-hover align-middle">
                            <thead className="table-light">
                                <tr>
                                    <th>ID</th>
                                    <th>Username</th>
                                    <th>Airline Name</th>
                                    <th>Email</th>
                                    <th>Phone</th>
                                </tr>
                            </thead>

                            <tbody>
                                {owners.length > 0 ? (
                                    owners.map((o) => (
                                        <tr key={o.id}>
                                            <td>{o.id}</td>
                                            <td>{o.username}</td>
                                            <td>{o.airline_name}</td>
                                            <td>{o.email}</td>
                                            <td>{o.phone}</td>
                                        </tr>
                                    ))
                                ) : (
                                    <tr>
                                        <td colSpan="5" className="text-center text-muted">
                                            No Owners Found
                                        </td>
                                    </tr>
                                )}
                            </tbody>
                        </table>

                        {/* Pagination */}
                        <div className="d-flex justify-content-between align-items-center mt-3">

                            <button
                                className="btn btn-outline-secondary"
                                disabled={page === 0}
                                onClick={() => setPage(page - 1)}
                            >
                                Previous
                            </button>

                            <span className="fw-semibold">
                                Page {page + 1} of {totalPages}
                            </span>

                            <button
                                className="btn btn-outline-secondary"
                                disabled={page === totalPages - 1 || totalPages === 0}
                                onClick={() => setPage(page + 1)}
                            >
                                Next
                            </button>

                        </div>

                    </div>
                </div>

            </div>
        </div>
    )
}

export default OwnerList