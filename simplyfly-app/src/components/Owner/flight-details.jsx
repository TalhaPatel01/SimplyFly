import { useEffect, useState } from "react"
import { useNavigate, useParams } from "react-router-dom"
import axios from "axios"
import OwnerNavbar from "./owner-navbar"

function FlightDetails() {
    const { routeFlightId } = useParams()

    const [flight, setFlight] = useState(undefined)
    const [loading, setLoading] = useState(true)
    const [error, setError] = useState(undefined)

    const navigate = useNavigate()

    useEffect(() => {
        const fetchFlightDetails = async () => {
            try {
                const token = localStorage.getItem("token")

                const res = await axios.get(
                    `http://localhost:8080/api/flight/get-details/${routeFlightId}`,
                    {
                        headers: {
                            Authorization: `Bearer ${token}`
                        }
                    }
                )

                setFlight(res.data)
            } catch (err) {
                console.log(err)
                setError("Failed to load flight details")
            } finally {
                setLoading(false)
            }
        }

        fetchFlightDetails()
    }, [routeFlightId])

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
                            ✈️ Flight Details
                        </h3>
                        <span className="badge bg-primary fs-6 px-3 py-2">
                            {flight.flightNumber}
                        </span>
                    </div>

                    {/* Route */}
                    <div className="text-center mb-4">
                        <h5 className="fw-semibold">
                            {flight.sourceCity} ➝ {flight.destinationCity}
                        </h5>
                        <div className="text-muted">
                            {flight.sourceAirport} ({flight.sourceAirportCode}) → {flight.destinationAirport} ({flight.destinationAirportCode})
                        </div>
                    </div>

                    {/* Time */}
                    <div className="row text-center mb-4">
                        <div className="col-md-6">
                            <div className="border rounded-3 p-3">
                                <h6 className="text-muted">Departure</h6>
                                <h5 className="fw-bold">{flight.departureTime}</h5>
                            </div>
                        </div>
                        <div className="col-md-6">
                            <div className="border rounded-3 p-3">
                                <h6 className="text-muted">Arrival</h6>
                                <h5 className="fw-bold">{flight.arrivalTime}</h5>
                            </div>
                        </div>
                    </div>

                    {/* Seats */}
                    <h5 className="text-primary mb-3">Seat Information</h5>
                    <div className="row g-3 mb-4">
                        <div className="col-md-3">
                            <div className="card p-3 text-center">
                                <h6>Total</h6>
                                <h5>{flight.totalSeats}</h5>
                            </div>
                        </div>
                        <div className="col-md-3">
                            <div className="card p-3 text-center">
                                <h6>Available</h6>
                                <h5 className="text-success">{flight.availableSeats}</h5>
                            </div>
                        </div>
                        <div className="col-md-3">
                            <div className="card p-3 text-center">
                                <h6>Economy</h6>
                                <h5>{flight.economySeats}</h5>
                            </div>
                        </div>
                        <div className="col-md-3">
                            <div className="card p-3 text-center">
                                <h6>Premium</h6>
                                <h5>{flight.premiumEconomySeats}</h5>
                            </div>
                        </div>

                        <div className="col-md-3">
                            <div className="card p-3 text-center">
                                <h6>Business</h6>
                                <h5>{flight.businessClassSeats}</h5>
                            </div>
                        </div>
                        <div className="col-md-3">
                            <div className="card p-3 text-center">
                                <h6>First Class</h6>
                                <h5>{flight.firstClassSeats}</h5>
                            </div>
                        </div>
                    </div>

                    {/* Fare */}
                    <h5 className="text-primary mb-3">Fare Details</h5>
                    <div className="row g-3 mb-4">
                        <div className="col-md-3"><div className="card p-3 text-center">Economy<br />₹{flight.economyFare}</div></div>
                        <div className="col-md-3"><div className="card p-3 text-center">Premium<br />₹{flight.premiumEconomyFare}</div></div>
                        <div className="col-md-3"><div className="card p-3 text-center">Business<br />₹{flight.businessClassFare}</div></div>
                        <div className="col-md-3"><div className="card p-3 text-center">First Class<br />₹{flight.firstClassFare}</div></div>
                    </div>

                    {/* Baggage */}
                    <h5 className="text-primary mb-3">Baggage</h5>
                    <div className="row g-3">
                        <div className="col-md-6">
                            <div className="card p-3 text-center">
                                <h6>Baggage Allowed</h6>
                                <h5>{flight.baggageAllowed} kg</h5>
                            </div>
                        </div>
                        <div className="col-md-6">
                            <div className="card p-3 text-center">
                                <h6>Hand Carry</h6>
                                <h5>{flight.handCarryAllowed} kg</h5>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
            <div className="text-center mt-4">
                <div className="d-flex justify-content-center gap-3 mt-4 mb-2">
                    <button
                        className="btn btn-primary px-4 py-2"
                        onClick={() => navigate(`/flight/passenger/${routeFlightId}`)}
                    >
                        View Passengers
                    </button>

                    <button
                        className="btn btn-primary px-4 py-2"
                        onClick={() => navigate(`/flight/seat/map/${routeFlightId}`,
                            {
                                state: {
                                    flightNumber: flight.flightNumber
                                }
                            }
                        )}
                    >
                        View Seat Map
                    </button>
                </div>
            </div>
        </div>
    )
}

export default FlightDetails