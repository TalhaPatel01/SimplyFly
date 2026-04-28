import React from "react";
import { useLocation, useNavigate } from "react-router-dom";

const PaymentSuccess = () => {
    const location = useLocation();
    const navigate = useNavigate();

    const data = location.state;

    // Handle refresh case
    if (!data) {
        return (
            <div className="text-center mt-5">
                <h4>No Payment Data Found</h4>
                <button
                    className="btn btn-primary mt-3"
                    onClick={() => navigate("/my-trips")}
                >
                    Go to My Trips
                </button>
            </div>
        );
    }

    return (
        <div className="container mt-4">

            {/* HEADER */}
            <div className="text-center mb-4">
                <h3 className="fw-bold text-success">Payment Successful ✅</h3>
                <p className="text-muted small">
                    Your booking is confirmed. Ticket generated successfully.
                </p>
            </div>

            {/* BOOKING SUMMARY */}
            <div className="card border-0 shadow-sm rounded-4 mb-3">
                <div className="card-body d-flex justify-content-between align-items-center">
                    <div>
                        <h6 className="fw-bold text-primary mb-1">
                            Booking ID: {data.booking_id}
                        </h6>
                        <small className="text-muted">
                            Status: {data.ticketStatus}
                        </small>
                    </div>

                    <div className="text-end">
                        <span className="badge bg-success px-3 py-2">
                            Confirmed
                        </span>
                    </div>
                </div>
            </div>

            {/* PASSENGER DETAILS */}
            <div className="card border-0 shadow-sm rounded-4 mb-3">
                <div className="card-body">
                    <h6 className="fw-bold mb-3">Passengers</h6>
                    <p className="mb-0">{data.passengerDetails}</p>
                </div>
            </div>

            {/* EXTRA INFO */}
            <div className="card border-0 shadow-sm rounded-4 mb-3">
                <div className="card-body d-flex justify-content-between">

                    <div>
                        <small className="text-muted">Baggage</small>
                        <p className="fw-semibold mb-0">
                            {data.baggageAllowed} kg
                        </p>
                    </div>

                    <div className="text-end">
                        <small className="text-muted">Booked At</small>
                        <p className="fw-semibold mb-0">
                            {new Date(data.createdAt).toLocaleString()}
                        </p>
                    </div>

                </div>
            </div>

            {/* ACTION BUTTONS */}
            <div className="text-center mt-4">

                <button
                    className="btn btn-primary px-4 me-2 rounded-3 shadow-sm"
                    onClick={() => navigate("/my-trips")}
                >
                    My Trips
                </button>

            </div>

        </div>
    );
};

export default PaymentSuccess;