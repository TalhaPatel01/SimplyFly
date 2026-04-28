import { useState } from "react"
import { useLocation, useNavigate } from "react-router-dom"
import UserNavbar from "./user-navbar"

function AddPassengers() {
    const location = useLocation()
    const navigate = useNavigate()
    const { flight, source, destination, travelDate, seatClass } = location.state || {}

    const [passengers, setPassengers] = useState([
        { name: "", age: "", gender: "", passengerType: "" }
    ])

    // Handle no data
    if (!location.state) {
        return <div>No data found</div>
    }

    // Add Passenger
    const addPassenger = () => {
        setPassengers([
            ...passengers,
            { name: "", age: "", gender: "", passengerType: "" }
        ])
    }

    // Remove Passenger
    const removePassenger = (index) => {
        const updated = passengers.filter((_, i) => i !== index)
        setPassengers(updated)
    }

    // Handle input change
    const handleChange = (index, field, value) => {
        const updated = [...passengers]
        updated[index][field] = value

        //SET passengerType BASED ON AGE
        if (field === "age") {
            const age = parseInt(value)

            if (!isNaN(age)) {
                if (age < 2) {
                    updated[index].passengerType = "INFANT"
                } else if (age <= 12) {
                    updated[index].passengerType = "CHILD"
                } else {
                    updated[index].passengerType = "ADULT"
                }
            } else {
                updated[index].passengerType = ""
            }
        }

        setPassengers(updated)
    }

    // Continue to seat page
    const handleContinue = () => {

        // Validation
        if (passengers.some(p => !p.name || !p.age || !p.gender || !p.passengerType)) {
            alert("Please fill all passenger details")
            return
        }

        navigate("/select-seats", {
            state: {
                routeFlightId: flight.routeFlightId,
                travelDate,
                seatClass,
                passengers,
                flight,
                source,
                destination,
            }
        })
    }

    return (
        <div className="container-fluid bg-light min-vh-100">

            {/* Navbar */}
            <UserNavbar />

            {/* Flight Info Card */}
            <div className="card border-0 shadow-sm rounded-3 mt-4 mx-3">
                <div className="card-body d-flex justify-content-between align-items-center">

                    <div>
                        <h6 className="fw-bold text-primary mb-1">
                            {source} → {destination}
                        </h6>
                        <span className="text-muted small">
                            {travelDate}
                        </span>

                        <div className="mt-1 small text-muted">
                            <div>
                                🧳 Baggage: {flight?.baggageAllowed ?? "N/A"} kg
                            </div>
                            <div>
                                🎒 Hand Carry: {flight?.handCarryAllowed ?? "N/A"} kg
                            </div>
                        </div>
                    </div>

                    <div className="text-end">
                        <p className="mb-0 fw-semibold">
                            {flight.airlineName}
                        </p>
                        <small className="text-muted">
                            {flight.flightNumber}
                        </small>
                    </div>

                </div>
            </div>

            {/* Passenger Forms */}
            <div className="mt-4 mx-3">

                <h5 className="mb-3 fw-semibold">Passenger Details</h5>

                {passengers.map((p, index) => (
                    <div key={index} className="card border-0 shadow-sm rounded-3 mb-3">
                        <div className="card-body">

                            <div className="d-flex justify-content-between align-items-center">
                                <h6 className="fw-semibold mb-2">
                                    Passenger {index + 1}
                                </h6>

                                {passengers.length > 1 && (
                                    <button
                                        className="btn btn-sm btn-outline-danger mb-2"
                                        onClick={() => removePassenger(index)}
                                    >
                                        Remove
                                    </button>
                                )}
                            </div>

                            <div className="row">

                                <div className="col-md-4 mb-2">
                                    <input
                                        type="text"
                                        className="form-control"
                                        placeholder="Full Name"
                                        value={p.name}
                                        onChange={(e) =>
                                            handleChange(index, "name", e.target.value)
                                        }
                                    />
                                </div>

                                <div className="col-md-4 mb-2">
                                    <input
                                        type="number"
                                        className="form-control"
                                        placeholder="Age"
                                        value={p.age}
                                        onChange={(e) =>
                                            handleChange(index, "age", e.target.value)
                                        }
                                    />

                                    {/* Optional display (no UI change, just info) */}
                                    {p.passengerType && (
                                        <small className="text-muted">
                                            {p.passengerType}
                                        </small>
                                    )}
                                </div>

                                <div className="col-md-4 mb-2">
                                    <select
                                        className="form-control"
                                        value={p.gender}
                                        onChange={(e) =>
                                            handleChange(index, "gender", e.target.value)
                                        }
                                    >
                                        <option value="">Select Gender</option>
                                        <option>Male</option>
                                        <option>Female</option>
                                    </select>
                                </div>

                            </div>

                        </div>
                    </div>
                ))}

                {/* Add Passenger Button */}
                <button
                    className="btn btn-outline-primary"
                    onClick={addPassenger}
                >
                    + Add Passenger
                </button>

                {/* Continue Button */}
                <div className="mt-4">
                    <button
                        className="btn btn-primary w-100"
                        onClick={handleContinue}
                    >
                        Continue to Seat Selection
                    </button>
                </div>

            </div>

        </div>
    );
}

export default AddPassengers