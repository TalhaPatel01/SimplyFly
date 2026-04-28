import { useParams } from "react-router-dom"
import { useEffect, useState } from "react"
import axios from "axios"
import OwnerNavbar from "./owner-navbar"

function BookingDetails() {

    const { bookingId } = useParams()
    const [booking, setBooking] = useState(null)

    useEffect(() => {
        fetchBooking()
    }, [])

    const fetchBooking = async () => {
        try {
            const token = localStorage.getItem("token")

            const res = await axios.get(
                `http://localhost:8080/api/booking/${bookingId}`,
                {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                }
            )

            setBooking(res.data)

        } catch (err) {
            console.error(err)
        }
    }

    if (!booking) {
        return <div className="text-center mt-5">Loading...</div>
    }

    return (
        <div className="container-fluid bg-light min-vh-100">

            {/* Navbar */}
            <div className="row">
                <OwnerNavbar />
            </div>

            <div className="container mt-4">

                {/* 🔹 Booking Summary */}
                <div className="card shadow border-0 rounded-4 p-4 mb-4">
                    <div className="d-flex justify-content-between align-items-center">

                        <div>
                            <h4 className="text-primary mb-1">
                                Booking #{booking.bookingId}
                            </h4>
                            <small className="text-muted">
                                {booking.source} → {booking.destination}
                            </small>
                        </div>

                        <div className="text-end">
                            <h5 className="text-success mb-1">
                                ₹{booking.totalAmount}
                            </h5>

                            <span className={`badge ${booking.bookingStatus === "CONFIRMED"
                                    ? "bg-success"
                                    : "bg-danger"
                                }`}>
                                {booking.bookingStatus}
                            </span>
                        </div>

                    </div>
                </div>

                {/* 🔹 Flight Info */}
                <div className="card shadow border-0 rounded-4 p-4 mb-4">

                    <h5 className="mb-3 text-primary">✈️ Flight Details</h5>

                    <div className="row">

                        <div className="col-md-3">
                            <p className="mb-1 text-muted">Airline</p>
                            <strong>{booking.flight.airlineName}</strong>
                        </div>

                        <div className="col-md-3">
                            <p className="mb-1 text-muted">Flight No</p>
                            <strong>{booking.flight.flightNumber}</strong>
                        </div>

                        <div className="col-md-3">
                            <p className="mb-1 text-muted">Departure</p>
                            <strong>{booking.flight.departureTime}</strong>
                        </div>

                        <div className="col-md-3">
                            <p className="mb-1 text-muted">Arrival</p>
                            <strong>{booking.flight.arrivalTime}</strong>
                        </div>

                    </div>

                    <hr />

                    <div className="row">

                        <div className="col-md-4">
                            <p className="mb-1 text-muted">From</p>
                            <strong>{booking.source}</strong>
                        </div>

                        <div className="col-md-4">
                            <p className="mb-1 text-muted">To</p>
                            <strong>{booking.destination}</strong>
                        </div>

                        <div className="col-md-4">
                            <p className="mb-1 text-muted">Travel Date</p>
                            <strong>{booking.travelDate}</strong>
                        </div>

                    </div>

                </div>

                {/* 🔹 Passenger List */}
                <div className="card shadow border-0 rounded-4 p-4">

                    <h5 className="mb-3 text-primary">👤 Passengers</h5>

                    <div className="table-responsive">
                        <table className="table table-hover align-middle">

                            <thead className="table-light">
                                <tr>
                                    <th>Name</th>
                                    <th>Age</th>
                                    <th>Gender</th>
                                    <th>Type</th>
                                    <th>Seat</th>
                                </tr>
                            </thead>

                            <tbody>
                                {
                                    booking.passengers.map((p, index) => (
                                        <tr key={index}>
                                            <td>{p.name}</td>
                                            <td>{p.age}</td>
                                            <td>{p.gender}</td>
                                            <td>
                                                <span className="badge bg-info text-dark">
                                                    {p.passengerType}
                                                </span>
                                            </td>
                                            <td className="fw-bold">
                                                {p.seatNumber}
                                            </td>
                                        </tr>
                                    ))
                                }
                            </tbody>

                        </table>
                    </div>

                </div>

            </div>
        </div>
    )
}

export default BookingDetails