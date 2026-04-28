import { useEffect, useState } from "react"
import axios from "axios"
import OwnerNavbar from "./owner-navbar"
import { useNavigate } from "react-router-dom"

function Flights() {
    const [flights, setFlights] = useState([])
    const [page, setPage] = useState(0)
    const [totalPages, setTotalPages] = useState(0)

    const navigate = useNavigate()

    const size = 5

    useEffect(() => {
        fetchFlights()
    }, [page])

    const fetchFlights = async () => {
        try {
            const config = {
                headers: {
                    "Authorization": "Bearer " + localStorage.getItem("token")
                }
            }

            const res = await axios.get(
                `http://localhost:8080/api/flight/get-by-owner?page=${page}&size=${size}`,
                config
            )

            setFlights(res.data.list)
            setTotalPages(res.data.totalPages)

        } catch (err) {
            console.log(err.message)
        }
    }

    // const handleDelete = async (id) => {
    //     try {
    //         await axios.delete(`http://localhost:8080/api/flights/${id}`, {
    //             withCredentials: true
    //         })
    //         fetchFlights()
    //     } catch (err) {
    //         console.error(err)
    //     }
    // }

    return (
        <div className="container-fluid bg-light min-vh-100">

            {/* Navbar */}
            <div className="row">
                <OwnerNavbar />
            </div>

            <div className="container mt-4">

                {/* Header */}
                <div className="d-flex justify-content-between align-items-center mb-3">
                    <h3 className="fw-bold">✈️ My Flights</h3>

                    <button className="btn btn-primary" onClick={() => navigate("/add-flight")}>
                        + Add Flight
                    </button>
                </div>

                {/* Table */}
                <div className="card shadow-sm border-0 rounded-4">
                    <div className="card-body">

                        <table className="table table-hover align-middle">
                            <thead className="table-light">
                                <tr>
                                    <th>Flight No</th>
                                    <th>Source</th>
                                    <th>Destination</th>
                                    <th>Departure</th>
                                    <th>Arrival</th>
                                    <th className="text-center">Actions</th>
                                </tr>
                            </thead>

                            <tbody>
                                {flights.length > 0 ? (
                                    flights.map((f) => (
                                        <tr key={f.routeFlightId}>
                                            <td className="fw-semibold">{f.flightNumber}</td>
                                            <td>{f.sourceCity}</td>
                                            <td>{f.destinationCity}</td>
                                            <td>{f.departureTime}</td>
                                            <td>{f.arrivalTime}</td>

                                            <td className="text-center">
                                                <button
                                                    className="btn btn-sm btn-outline-primary me-2"
                                                    onClick={() => navigate(`/view/flight/details/${f.routeFlightId}`)}
                                                >
                                                    View Details
                                                </button>

                                                <button
                                                    className="btn btn-sm btn-outline-warning me-2"
                                                    onClick={() => navigate(`/edit-time/${f.routeFlightId}`)}
                                                >
                                                    Edit Time
                                                </button>
                                            </td>
                                        </tr>
                                    ))
                                ) : (
                                    <tr>
                                        <td colSpan="6" className="text-center text-muted">
                                            No Flights Found
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
                                disabled={page === totalPages - 1}
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

export default Flights