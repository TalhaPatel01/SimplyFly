import { useEffect, useState } from "react"
import { useParams } from "react-router-dom"
import axios from "axios"
import OwnerNavbar from "./owner-navbar"

function FlightPassengers() {

    const { routeFlightId } = useParams()

    const [passengers, setPassengers] = useState([])
    const [page, setPage] = useState(0)
    const [size] = useState(5)

    const [totalPages, setTotalPages] = useState(0)
    const [totalRecords, setTotalRecords] = useState(0)

    const [loading, setLoading] = useState(true)
    const [error, setError] = useState(null)

    useEffect(() => {
        fetchPassengers()
    }, [routeFlightId, page])

    const fetchPassengers = async () => {
        try {
            setLoading(true)

            const token = localStorage.getItem("token")

            const res = await axios.get(
                `http://localhost:8080/api/passenger/${routeFlightId}?page=${page}&size=${size}`,
                {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                }
            )

            setPassengers(res.data.list)
            setTotalPages(res.data.totalPages)
            setTotalRecords(res.data.totalRecords)

        } catch (err) {
            console.error(err)
            setError("Failed to load passengers")
        } finally {
            setLoading(false)
        }
    }

    if (loading) return <h5 className="text-center mt-5">Loading...</h5>
    if (error) return <h5 className="text-danger text-center mt-5">{error}</h5>

    return (
        <div className="container-fluid bg-light min-vh-100">

            <div className="row">
                <OwnerNavbar />
            </div>

            <div className="container mt-4">

                <div className="card shadow border-0 rounded-4 p-4">

                    {/* Header */}
                    <div className="d-flex justify-content-between align-items-center mb-3">
                        <h3 className="fw-bold text-primary">
                            👥 Passenger List
                        </h3>
                        <span className="badge bg-secondary px-3 py-2">
                            Total: {totalRecords}
                        </span>
                    </div>

                    {/* Table */}
                    {passengers.length === 0 ? (
                        <h6 className="text-center text-muted">
                            No passengers found
                        </h6>
                    ) : (
                        <>
                            <div className="table-responsive">
                                <table className="table table-hover align-middle text-center">

                                    <thead className="table-primary">
                                        <tr>
                                            <th>#</th>
                                            <th>Name</th>
                                            <th>Age</th>
                                            <th>Gender</th>
                                            <th>Type</th>
                                            <th>Seat</th>
                                            <th>Class</th>
                                        </tr>
                                    </thead>

                                    <tbody>
                                        {passengers.map((p, index) => (
                                            <tr key={index}>
                                                <td>{page * size + index + 1}</td>
                                                <td className="fw-semibold">{p.name}</td>
                                                <td>{p.age}</td>
                                                <td>{p.gender}</td>

                                                <td>
                                                    <span className={`badge ${
                                                        p.passengerType === "INFANT"
                                                            ? "bg-warning text-dark"
                                                            : "bg-info text-dark"
                                                    }`}>
                                                        {p.passengerType}
                                                    </span>
                                                </td>

                                                <td>
                                                    {p.seatNumber || "N/A"}
                                                </td>

                                                <td>
                                                    {p.seatClass || "INFANT"}
                                                </td>
                                            </tr>
                                        ))}
                                    </tbody>

                                </table>
                            </div>

                            {/* Pagination Controls */}
                            <div className="d-flex justify-content-between align-items-center mt-3">

                                <button
                                    className="btn btn-outline-primary"
                                    disabled={page === 0}
                                    onClick={() => setPage(prev => prev - 1)}
                                >
                                    ⬅ Previous
                                </button>

                                <span className="fw-semibold">
                                    Page {page + 1} of {totalPages}
                                </span>

                                <button
                                    className="btn btn-outline-primary"
                                    disabled={page + 1 >= totalPages}
                                    onClick={() => setPage(prev => prev + 1)}
                                >
                                    Next ➡
                                </button>

                            </div>
                        </>
                    )}

                </div>
            </div>
        </div>
    )
}

export default FlightPassengers