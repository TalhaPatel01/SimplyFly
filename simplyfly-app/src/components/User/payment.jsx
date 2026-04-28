import React, { useState } from "react";
import axios from "axios";
import { useLocation, useNavigate } from "react-router-dom";

const Payment = () => {
    const location = useLocation()
    const navigate = useNavigate()

    const {
        booking,
        flight,
        passengers,
        travelDate,
        seatClass,
        source,
        destination
    } = location.state

    const [paymentMethod, setPaymentMethod] = useState("")

    // // Calculate total
    // const farePerPerson = flight.fare
    // const totalAmount = farePerPerson * passengers.length

    const handlePayment = async () => {
        if (!paymentMethod) {
            alert("Please select a payment method");
            return
        }

        try {
            const config = {
                headers: {
                    "Authorization": "Bearer " + localStorage.getItem("token")
                }
            }
            const response = await axios.post(
                "http://localhost:8080/api/payment/make-payment", {
                bookingId: booking.bookingId,
                paymentMethod: paymentMethod
            }, config)

            // Navigate to success page
            navigate("/booking-success", {
                state: {
                    ...response.data,
                    flight,
                    source,
                    destination,
                    travelDate,
                    seatClass
                }
            })

        } catch (err) {
            console.error(err);
            alert("Payment Failed. Try again.")
        }
    }

    return (
        <div className="container-fluid">
            <div className="container mt-4">

                {/* Booking Info */}
                <div className="card mb-4 shadow-sm">
                    <div className="card-body d-flex justify-content-between">
                        <div>
                            <h5>Booking ID: {booking.bookingId}</h5>
                            <small className="text-muted">
                                Status: {booking.status || "PENDING"}
                            </small>
                        </div>

                        <div className="text-end">
                            <div className="fw-bold">{travelDate}</div>
                            <small>Date</small>
                        </div>
                    </div>
                </div>

                {/* Flight Details */}
                <div className="card mb-4 shadow-sm">
                    <div className="card-body d-flex justify-content-between">

                        <div>
                            <h5>{flight.airlineName} ({flight.flightNumber})</h5>
                            <small className="text-muted">
                                {source} → {destination}
                            </small>
                        </div>

                        <div className="text-center">
                            <div className="fw-bold">{flight.departureTime}</div>
                            <small>Departure</small>
                        </div>

                        <div className="text-center">
                            <div className="fw-bold">{flight.arrivalTime}</div>
                            <small>Arrival</small>
                        </div>

                    </div>
                </div>

                {/* Passenger Details */}
                <div className="card mb-4">
                    <div className="card-body">
                        <h5>Passengers</h5>

                        <table className="table mt-3">
                            <thead>
                                <tr>
                                    <th>Name</th>
                                    <th>Seat</th>
                                </tr>
                            </thead>
                            <tbody>
                                {passengers.map((p, index) => (
                                    <tr key={index}>
                                        <td>{p.name}</td>
                                        <td>{p.seatNumber}</td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    </div>
                </div>

                {/* Payment Summary */}
                <div className="card mb-4">
                    <div className="card-body">
                        <h5>Payment Summary</h5>

                        {/* <p>Fare per passenger: ₹{farePerPerson}</p>
                        <p>Passengers: {passengers.length}</p>
                        <hr /> */}
                        <h5>Total Amount: ₹{booking.totalAmount}</h5>
                    </div>
                </div>

                {/* Payment Method */}
                <div className="card mb-4">
                    <div className="card-body">
                        <h5>Select Payment Method</h5>

                        <div className="form-check mt-3">
                            <input
                                type="radio"
                                className="form-check-input"
                                name="paymentMethod"
                                value="UPI"
                                onChange={(e) => setPaymentMethod(e.target.value)}
                            />
                            <label className="form-check-label">UPI</label>
                        </div>

                        <div className="form-check">
                            <input
                                type="radio"
                                className="form-check-input"
                                name="paymentMethod"
                                value="CARD"
                                onChange={(e) => setPaymentMethod(e.target.value)}
                            />
                            <label className="form-check-label">Card</label>
                        </div>

                        <div className="form-check">
                            <input
                                type="radio"
                                className="form-check-input"
                                name="paymentMethod"
                                value="NETBANKING"
                                onChange={(e) => setPaymentMethod(e.target.value)}
                            />
                            <label className="form-check-label">Net Banking</label>
                        </div>
                    </div>
                </div>

                {/* Pay Button */}
                <div className="text-center">
                    <button
                        className="btn btn-primary mb-2"
                        onClick={handlePayment}
                    >
                        Pay Now
                    </button>
                </div>

            </div>
        </div>
    )
}

export default Payment