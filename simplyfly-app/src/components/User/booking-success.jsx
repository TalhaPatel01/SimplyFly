import React from "react";
import { useLocation, useNavigate } from "react-router-dom";

const BookingSuccess = () => {
    const location = useLocation()
    const navigate = useNavigate()

    const {
        booking_id,
        passengerDetails,
        ticketStatus,
        baggageAllowed,
        flight,
        source,
        destination,
        travelDate
    } = location.state || {}

    return (
        <div className="container mt-4">

            <div className="alert alert-success text-center">
                <h4>Booking Confirmed ✅</h4>
                <p>Your ticket has been successfully booked!</p>
            </div>

            {/* Ticket Card */}
            <div className="card shadow-lg mb-4">
                <div className="card-body">

                    {/* Top Section */}
                    <div className="d-flex justify-content-between mb-3">
                        <div>
                            <h5>Booking ID: {booking_id}</h5>
                            <small className="text-muted">
                                Status: {ticketStatus}
                            </small>
                        </div>

                        <div className="text-end">
                            <h6>{travelDate}</h6>
                            <small>Date of Travel</small>
                        </div>
                    </div>

                    <hr />

                    {/* Flight Info */}
                    <div className="d-flex justify-content-between mb-3">

                        <div>
                            <h5>{flight?.airlineName} ({flight?.flightNumber})</h5>
                            <small className="text-muted">
                                {source} → {destination}
                            </small>
                        </div>

                        <div className="text-center">
                            <div className="fw-bold">{flight?.departureTime}</div>
                            <small>Departure</small>
                        </div>

                        <div className="text-center">
                            <div className="fw-bold">{flight?.arrivalTime}</div>
                            <small>Arrival</small>
                        </div>

                    </div>

                    <hr />

                    {/* Passenger Details */}
                    <div className="mb-3">
                        <h5>Passenger Details</h5>
                        <pre style={{ background: "#f8f9fa", padding: "10px" }}>
                            {passengerDetails}
                        </pre>
                    </div>

                    {/* Baggage */}
                    <div className="mb-3">
                        <h6>Baggage Allowed: {flight.baggageAllowed} kg</h6>
                        <h6>Hand Carry Allowed: {flight.handCarryAllowed}</h6>
                    </div>

                </div>
            </div>

            {/* Action Buttons */}
            <div className="d-flex justify-content-center gap-3">

                <button
                    className="btn btn-primary"
                    onClick={() => navigate("/user-dashboard")}
                >
                    Go to Home
                </button>

                <button
                    className="btn btn-outline-success"
                    onClick={() => navigate("/my-trips")}
                >
                    View My Trips
                </button>

            </div>

        </div>
    );
};

export default BookingSuccess