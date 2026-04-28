import { useEffect, useState } from "react"
import axios from "axios"
import { useNavigate } from "react-router-dom"
import OwnerNavbar from "./owner-navbar"

function BookingList() {

    const [bookings, setBookings] = useState([])
    const [page, setPage] = useState(0)
    const [totalPages, setTotalPages] = useState(0)

    const navigate = useNavigate()

    useEffect(() => {
        fetchBookings()
    }, [page])

    const fetchBookings = async () => {
        try {
            const token = localStorage.getItem("token")

            const res = await axios.get(
                `http://localhost:8080/api/booking/get-by-owner?page=${page}&size=5`,
                {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                }
            )

            setBookings(res.data.list)
            setTotalPages(res.data.totalPages)

        } catch (err) {
            console.error(err)
        }
    }

    const handleViewDetails = (bookingId) => {
        navigate(`/owner/bookings/${bookingId}`)
    }

    return (
        <div className="container-fluid bg-light min-vh-100">

            {/* Navbar */}
            <div className="row">
                <OwnerNavbar />
            </div>

            <div className="container mt-4">

                <div className="card shadow border-0 rounded-4 p-3">

                    <h4 className="text-primary mb-3">📄 Bookings</h4>

                    <div className="table-responsive">
                        <table className="table table-hover align-middle">

                            <thead className="table-light">
                                <tr>
                                    <th>ID</th>
                                    <th>Flight No</th>
                                    <th>Source</th>
                                    <th>Destination</th>
                                    <th>Date</th>
                                    <th>Amount</th>
                                    <th>Status</th>
                                    <th className="text-center">Action</th>
                                </tr>
                            </thead>

                            <tbody>
                                {
                                    bookings.map((b) => (
                                        <tr key={b.bookingId}>

                                            <td>#{b.bookingId}</td>

                                            <td>{b.flight.flightNumber}</td>

                                            <td>{b.source}</td>

                                            <td>{b.destination}</td>

                                            <td>{b.travelDate}</td>

                                            <td className="fw-bold text-success">
                                                ₹{b.totalAmount}
                                            </td>

                                            <td>
                                                <span className={`badge ${b.bookingStatus === "CONFIRMED"
                                                        ? "bg-success"
                                                        : "bg-danger"
                                                    }`}>
                                                    {b.bookingStatus}
                                                </span>
                                            </td>

                                            <td className="text-center">
                                                <button
                                                    className="btn btn-outline-primary btn-sm"
                                                    onClick={() => handleViewDetails(b.bookingId)}
                                                >
                                                    View Details
                                                </button>
                                            </td>

                                        </tr>
                                    ))
                                }
                            </tbody>

                        </table>
                    </div>

                    {/* Pagination */}
                    <div className="d-flex justify-content-between mt-3">

                        <button
                            className="btn btn-outline-secondary"
                            disabled={page === 0}
                            onClick={() => setPage(page - 1)}
                        >
                            Previous
                        </button>

                        <span className="align-self-center">
                            Page {page + 1} of {totalPages}
                        </span>

                        <button
                            className="btn btn-outline-secondary"
                            disabled={page + 1 >= totalPages}
                            onClick={() => setPage(page + 1)}
                        >
                            Next
                        </button>

                    </div>

                </div>
            </div>
        </div>
    )
}

export default BookingList