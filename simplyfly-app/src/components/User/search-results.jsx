import { useLocation, useNavigate } from "react-router-dom";
import { useState } from "react";
import UserNavbar from "./user-navbar"
import LandingNavbar from "../Landing-page/landing-navbar"
import axios from "axios";

function SearchResults() {
    const location = useLocation();
    const navigate = useNavigate();

    const {
        source,
        destination,
        travelDate,
        seatClass,
        flights: initialFlights
    } = location.state || {};

    const [flights] = useState(initialFlights || []);

    if (!location.state) {
        return <div className="text-center mt-5">No search data found</div>;
    }

    const isLoggedIn = () => {
        return localStorage.getItem("token") !== null
    }

    const processBook = async (flight) => {
        try {
            const response = await axios.get(`http://localhost:8080/api/route/flight/flight-details/${flight.routeFlightId}`)
            const flightDetails = response.data

            const bookingState = {
                flight:{
                    ...flight,
                    baggageAllowed: flightDetails.baggageAllowed,
                    handCarryAllowed: flightDetails.handCarryAllowed
                },
                source,
                destination,
                travelDate,
                seatClass
            };

            if (!isLoggedIn()) {
                navigate("/login", {
                    state: {
                        redirectTo: "/passengers",
                        bookingData: bookingState
                    }
                });
            } else {
                navigate("/passengers", {
                    state: bookingState
                });
            }
        }
        catch(err){

        }
    }

    const formatDuration = (mins) => {
        const h = Math.floor(mins / 60);
        const m = mins % 60;
        return `${h}h ${m}m`;
    };

    const formatTime = (time) => time?.substring(0, 5);

    return (
        <div className="container-fluid bg-light min-vh-100">

            {/* Navbar */}
            {isLoggedIn() ? <UserNavbar /> : <LandingNavbar />}

            {/* Search Info */}
            <div className="card border-0 shadow-sm rounded-3 mt-4 mx-3">
                <div className="card-body d-flex justify-content-between align-items-center">
                    <div>
                        <h6 className="fw-bold text-primary mb-1">
                            {source} → {destination}
                        </h6>
                        <span className="text-muted small">
                            {travelDate}
                        </span>
                    </div>

                    <button
                        className="btn btn-outline-primary btn-sm"
                        onClick={() => navigate("/user-dashboard")}
                    >
                        Modify Search
                    </button>
                </div>
            </div>

            {/* Flight List */}
            <div className="mt-4 mx-3">

                {flights.length === 0 ? (
                    <p className="text-center text-muted">No flights found</p>
                ) : (
                    flights.map((flight) => (
                        <div key={flight.routeFlightId} className="card border-0 shadow-sm rounded-3 mb-3">
                            <div className="card-body">

                                <div className="row align-items-center">

                                    {/* Airline */}
                                    <div className="col-md-2">
                                        <p className="fw-semibold mb-1">
                                            {flight.airlineName}
                                        </p>
                                        <small className="text-muted">
                                            {flight.flightNumber}
                                        </small>
                                    </div>

                                    {/* Departure */}
                                    <div className="col-md-3 text-center">
                                        <p className="fw-bold mb-0">
                                            {formatTime(flight.departureTime)}
                                        </p>
                                        <small className="text-muted">
                                            {flight.source}
                                        </small>
                                    </div>

                                    {/* Duration */}
                                    <div className="col-md-2 text-center">
                                        <small className="text-muted">
                                            {formatDuration(flight.duration)}
                                        </small>
                                    </div>

                                    {/* Arrival */}
                                    <div className="col-md-3 text-center">
                                        <p className="fw-bold mb-0">
                                            {formatTime(flight.arrivalTime)}
                                        </p>
                                        <small className="text-muted">
                                            {flight.destination}
                                        </small>
                                    </div>

                                    {/* Price + Button */}
                                    <div className="col-md-2 text-end">
                                        <p className="fw-bold text-primary mb-2">
                                            ₹{flight.fare}/Adult
                                        </p>

                                        <button
                                            className="btn btn-primary btn-sm"
                                            onClick={() => processBook(flight)}
                                        >
                                            Book
                                        </button>
                                    </div>

                                </div>

                            </div>
                        </div>
                    ))
                )}

            </div>

        </div>
    );
}

export default SearchResults;