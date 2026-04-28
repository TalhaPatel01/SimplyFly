import React, { useState } from "react";
import axios from "axios";
import { useLocation, useNavigate } from "react-router-dom";
import UserNavbar from "./user-navbar";

const ConfirmBooking = () => {
    const location = useLocation()
    const navigate = useNavigate()

    const {
        routeFlightId,
        passengers,
        flight,
        travelDate,
        seatClass,
        source,
        destination
    } = location.state

    const [errMsg, setErrMsg] = useState(undefined)

    const baseFare = flight.fare;

    let adultFare = 0;
    let childFare = 0;
    let infantFare = 0;

    passengers.forEach(p => {
        if (p.passengerType === "ADULT") {
            adultFare += baseFare;
        } 
        else if (p.passengerType === "CHILD") {
            childFare += baseFare;
        } 
        else if (p.passengerType === "INFANT") {
            infantFare += baseFare * 0.1;
        }
    });

    const totalFare = adultFare + childFare + infantFare;

    const proceedToDocumentUpload = async () => {
        try {
            const config = {
                headers: {
                    "Authorization": "Bearer " + localStorage.getItem("token")
                }
            }

            const response = await axios.post(
                "http://localhost:8080/api/booking/create-booking", {
                routeFlightId,
                travelDate,
                seatClass,
                passengerList: passengers
            }, config);

            const booking = response.data;
            console.log(booking)

            navigate("/upload-document", {
                state: {
                    booking,
                    flight,
                    passengers,
                    travelDate,
                    seatClass,
                    source,
                    destination
                }
            });

        } catch (err) {
            setErrMsg(err.message)
        }
    }

    return (
        <div className="container-fluid">
            <UserNavbar/>
            <div className="container mt-4">

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

                        <div className="text-end">
                            <div className="fw-bold">{travelDate}</div>
                            <small>Date</small>
                        </div>

                    </div>
                </div>

                {/* Passenger Details */}
                <div className="card mb-4">
                    <div className="card-body">
                        <h5>Passenger Details</h5>

                        <table className="table mt-3">
                            <thead>
                                <tr>
                                    <th>Name</th>
                                    <th>Age</th>
                                    <th>Gender</th>
                                    <th>Seat</th>
                                </tr>
                            </thead>
                            <tbody>
                                {passengers.map((p, index) => (
                                    <tr key={index}>
                                        <td>{p.name}</td>
                                        <td>{p.age}</td>
                                        <td>{p.gender}</td>
                                        <td>{p.seatNumber}</td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    </div>
                </div>

                {/* Seat Summary */}
                <div className="card mb-4">
                    <div className="card-body">
                        <h5>Seat Summary</h5>
                        <p>
                            {passengers.map(p => p.seatNumber).join(", ")}
                        </p>
                    </div>
                </div>

                {/* Fare Summary */}
                <div className="card mb-4">
                    <div className="card-body">
                        <h5>Fare Summary</h5>

                        <p>Adult Fare: ₹{adultFare}</p>
                        <p>Child Fare: ₹{childFare}</p>
                        <p>Infant Fare: ₹{infantFare}</p>

                        <hr />

                        <h5>Total: ₹{totalFare}</h5>

                        {/* Optional per passenger */}
                        <ul className="mt-3">
                            {passengers.map((p, i) => {
                                let fare = baseFare;
                                if (p.passengerType === "INFANT") {
                                    fare = baseFare * 0.1;
                                }

                                return (
                                    <li key={i}>
                                        {p.name} ({p.passengerType}) → ₹{fare}
                                    </li>
                                );
                            })}
                        </ul>
                    </div>
                </div>

                {/* Error */}
                {errMsg && (
                    <div className="alert alert-danger">
                        {errMsg}
                    </div>
                )}

                {/* Proceed */}
                <div className="text-center">
                    <button
                        className="btn btn-success mb-2"
                        onClick={proceedToDocumentUpload}
                    >
                        Upload ID Proof
                    </button>
                </div>

            </div>
        </div>
    );
};

export default ConfirmBooking;