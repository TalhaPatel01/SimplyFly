import { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import UserNavbar from "./user-navbar";

function MyTrips() {
    const [trips, setTrips] = useState([]);
    const navigate = useNavigate();

    const api = "http://localhost:8080/api/booking/get-by-user";

    useEffect(() => {
        const fetchTrips = async () => {
            try {
                const config = {
                    headers: {
                        Authorization: "Bearer " + localStorage.getItem("token"),
                    },
                };

                const response = await axios.get(api, config);
                setTrips(response.data);
            } catch (err) {
                console.log(err.message);
            }
        };

        fetchTrips();
    }, []);

    // Redirect to Payment Page
    const handlePayment = (bookingId) => {
        navigate(`/payment/${bookingId}`);
    };

    // Cancel Booking
    const handleCancel = async (bookingId) => {
        try {
            const config = {
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("token"),
                },
            };

            await axios.put(
                `http://localhost:8080/api/booking/cancel-booking/${bookingId}`,
                {},
                config
            );

            // update UI instantly
            setTrips((prev) =>
                prev.map((trip) =>
                    trip.bookingId === bookingId
                        ? { ...trip, bookingStatus: "CANCELLED" }
                        : trip
                )
            );

            alert("Booking cancelled successfully");
        } catch (err) {
            console.log(err.message);
        }
    };

    return (
        <div className="container-fluid bg-light min-vh-100">
            <UserNavbar />

            <div className="container mt-4">
                <h3 className="mb-4 text-center text-primary fw-bold">
                    My Trips
                </h3>

                <div className="row">
                    {trips.map((trip) => (
                        <div className="col-md-6 mb-4" key={trip.bookingId}>
                            <div className="card border-0 shadow rounded-4 overflow-hidden">

                                {/* Header */}
                                <div className="card-header bg-primary text-white d-flex justify-content-between align-items-center py-3">
                                    <div>
                                        <h6 className="mb-0 fw-bold">{trip.airlineName}</h6>
                                        <small>Flight {trip.flightNumber}</small>
                                    </div>

                                    <span
                                        className={`badge px-3 py-2 ${trip.bookingStatus === "CONFIRMED"
                                                ? "bg-success"
                                                : trip.bookingStatus === "PENDING"
                                                    ? "bg-warning text-dark"
                                                    : "bg-danger"
                                            }`}
                                    >
                                        {trip.bookingStatus}
                                    </span>
                                </div>

                                {/* Body */}
                                <div className="card-body">

                                    {/* Route */}
                                    <div className="text-center mb-3">
                                        <h5 className="fw-bold text-dark">
                                            {trip.source} → {trip.destination}
                                        </h5>
                                        <small className="text-muted">{trip.travelDate}</small>
                                    </div>

                                    {/* Time */}
                                    <div className="d-flex justify-content-between text-center px-3">
                                        <div>
                                            <p className="mb-1 text-muted small">Departure</p>
                                            <p className="fw-semibold">{trip.departureTime}</p>
                                        </div>

                                        <div className="align-self-center text-muted">✈</div>

                                        <div>
                                            <p className="mb-1 text-muted small">Arrival</p>
                                            <p className="fw-semibold">{trip.arrivalTime}</p>
                                        </div>
                                    </div>

                                    <hr />

                                    {/* Footer */}
                                    <div className="d-flex justify-content-between align-items-center">
                                        <span className="text-muted">
                                            👤 {trip.passengerCount} Passenger(s)
                                        </span>

                                        <div>
                                            {trip.bookingStatus === "PENDING" && (
                                                <button
                                                    className="btn btn-warning btn-sm me-2"
                                                    onClick={() => handlePayment(trip.bookingId)}
                                                >
                                                    Pay Now
                                                </button>
                                            )}

                                            {trip.bookingStatus === "CONFIRMED" && (
                                                <>
                                                    <button
                                                        className="btn btn-outline-success btn-sm me-2"
                                                        onClick={() => navigate(`/ticket/${trip.bookingId}`)}
                                                    >
                                                        Download Ticket
                                                    </button>

                                                    <button
                                                        className="btn btn-outline-danger btn-sm"
                                                        onClick={() => handleCancel(trip.bookingId)}
                                                    >
                                                        Cancel Booking
                                                    </button>
                                                </>
                                            )}
                                        </div>
                                    </div>

                                </div>
                            </div>
                        </div>
                    ))}
                </div>
            </div>
        </div>
    );
}

export default MyTrips;