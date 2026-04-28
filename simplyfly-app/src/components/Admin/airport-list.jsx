import { useEffect, useState } from "react"
import axios from "axios"
import AdminNavbar from "./admin-navbar"

function AirportList() {

    const [airports, setAirports] = useState([])
    const [page, setPage] = useState(0)
    const [totalPages, setTotalPages] = useState(0)

    const [showForm, setShowForm] = useState(false)

    const [form, setForm] = useState({
        name: "",
        code: "",
        city: "",
        country: ""
    })

    const size = 5

    useEffect(() => {
        fetchAirports()
    }, [page])

    const fetchAirports = async () => {
        try {
            const config = {
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("token")
                }
            }

            const res = await axios.get(
                `http://localhost:8080/api/airport/pagination/get-all?page=${page}&size=${size}`,
                config
            )

            setAirports(res.data.list)
            setTotalPages(res.data.totalPages)

        } catch (err) {
            console.log(err.message)
        }
    }

    const handleChange = (e) => {
        setForm({ ...form, [e.target.name]: e.target.value })
    }

    const addAirport = async () => {
        try {
            const config = {
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("token")
                }
            }

            await axios.post(
                "http://localhost:8080/api/airport/add",
                form,
                config
            )

            setForm({
                name: "",
                code: "",
                city: "",
                country: ""
            })

            setShowForm(false)
            fetchAirports()

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
                    <h3 className="fw-bold">🛫 Airports</h3>

                    <button
                        className="btn btn-primary"
                        onClick={() => setShowForm(!showForm)}
                    >
                        + Add Airport
                    </button>
                </div>

                {/* Add Airport Form */}
                {showForm && (
                    <div className="card p-3 mb-3 shadow-sm">
                        <div className="row g-2 align-items-end">

                            <div className="col-md-3">
                                <label className="form-label small">Name</label>
                                <input
                                    className="form-control"
                                    name="name"
                                    value={form.name}
                                    onChange={handleChange}
                                />
                            </div>

                            <div className="col-md-2">
                                <label className="form-label small">Code</label>
                                <input
                                    className="form-control"
                                    name="code"
                                    placeholder="e.g. DEL"
                                    value={form.code}
                                    onChange={handleChange}
                                />
                            </div>

                            <div className="col-md-3">
                                <label className="form-label small">City</label>
                                <input
                                    className="form-control"
                                    name="city"
                                    value={form.city}
                                    onChange={handleChange}
                                />
                            </div>

                            <div className="col-md-3">
                                <label className="form-label small">Country</label>
                                <input
                                    className="form-control"
                                    name="country"
                                    value={form.country}
                                    onChange={handleChange}
                                />
                            </div>

                            <div className="col-md-1">
                                <button
                                    className="btn btn-success w-100"
                                    onClick={addAirport}
                                >
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
                                    <th>Code</th>
                                    <th>Name</th>
                                    <th>City</th>
                                    <th>Country</th>
                                </tr>
                            </thead>

                            <tbody>
                                {airports.length > 0 ? (
                                    airports.map((a) => (
                                        <tr key={a.airport_id}>
                                            <td>{a.airport_id}</td>
                                            <td>{a.code}</td>
                                            <td>{a.name}</td>
                                            <td>{a.city}</td>
                                            <td>{a.country}</td>
                                        </tr>
                                    ))
                                ) : (
                                    <tr>
                                        <td colSpan="5" className="text-center text-muted">
                                            No Airports Found
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

export default AirportList