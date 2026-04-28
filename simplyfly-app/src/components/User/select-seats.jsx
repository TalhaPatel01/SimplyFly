import React, { useEffect, useState } from "react";
import axios from "axios";
import { useLocation, useNavigate } from "react-router-dom";
import UserNavbar from "./user-navbar";

const SeatSelection = () => {
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

    const [seatsData, setSeatsData] = useState([])
    const [selectedSeats, setSelectedSeats] = useState([])
    const [errMsg, setErrMsg] = useState(undefined)

    if (!location.state) {
        return <div>No data found</div>
    }

    const nonInfantPassengers = passengers.filter(
        (p) => p.passengerType !== "INFANT"
    )

    // Fetch Seats
    useEffect(() => {
        const fetchSeats = async () => {
            try {
                const response = await axios.get(
                    `http://localhost:8080/api/seat/get-all?routeFlightId=${routeFlightId}&seatClass=${seatClass}`
                );
                setSeatsData(response.data)
            } catch (err) {
                setErrMsg(err.message)
            }
        };

        fetchSeats();
    }, [routeFlightId])

    // Group seats by row
    const groupedSeats = seatsData.reduce((acc, seat) => {
        if (!acc[seat.row]) acc[seat.row] = []
        acc[seat.row].push(seat)
        return acc
    }, {})

    const sortedRows = Object.keys(groupedSeats).sort((a, b) => a - b);

    const getSortedSeats = (rowSeats) => {
        return rowSeats.sort((a, b) =>
            a.column.localeCompare(b.column)
        )
    }

    // Seat Click
    const handleSeatClick = (seat) => {
        if (seat.seatStatus === "BOOKED") return;

        const exists = selectedSeats.find(
            (s) => s.seatNumber === seat.seatNumber
        )

        if (exists) {
            setSelectedSeats((prev) =>
                prev.filter((s) => s.seatNumber !== seat.seatNumber)
            )
        } else {
            if (selectedSeats.length >= nonInfantPassengers.length) {
                alert("You can only select seats for non-infant passengers")
                return
            }
            setSelectedSeats((prev) => [...prev, seat])
        }
    }

    const getSeatClass = (seat) => {
        if (seat.seatStatus === "BOOKED") return "btn-danger"
        if (selectedSeats.find((s) => s.seatNumber === seat.seatNumber))
            return "btn-success"
        return "btn-outline-secondary"
    }

    let seatIndex = 0;
    const mappedPassengers = passengers.map((p) => {
        if (p.passengerType === "INFANT") {
            return { ...p, seatNumber: null }
        } else {
            const seat = selectedSeats[seatIndex++]
            return {
                ...p,
                seatNumber: seat ? seat.seatNumber : null
            }
        }
    })

    const handleContinue = () => {
        if (selectedSeats.length !== nonInfantPassengers.length) {
            alert("Select seat for all non-infant passengers");
            return;
        }

        navigate("/confirm-booking", {
            state: {
                routeFlightId,
                passengers: mappedPassengers,
                flight,
                travelDate,
                seatClass,
                source,
                destination
            },
        });
    };

    return (
        <div className="container-fluid">
            <UserNavbar />

            <div className="container mt-4">

                {/* Flight Details Card */}
                <div className="card mb-4 shadow-sm">
                    <div className="card-body">

                        <div className="d-flex justify-content-between align-items-center">

                            <div>
                                <h5 className="mb-1">
                                    {flight.airlineName} ({flight.flightNumber})
                                </h5>
                                <small className="text-muted">
                                    {source} → {destination}
                                </small>
                            </div>

                            <div className="text-center">
                                <div className="fw-bold">
                                    {flight.departureTime}
                                </div>
                                <small className="text-muted">Departure</small>
                            </div>

                            <div className="text-center">
                                <div className="fw-bold">
                                    {flight.arrivalTime}
                                </div>
                                <small className="text-muted">Arrival</small>
                            </div>

                            <div className="text-end">
                                <div className="fw-bold">
                                    {travelDate}
                                </div>
                                <small className="text-muted">Date</small>

                                <div className="mt-2">
                                    <span className="badge bg-primary">
                                        {seatClass.replace("_", " ")}
                                    </span>
                                </div>
                            </div>

                        </div>

                    </div>
                </div>

                <h4 className="text-center mb-3">Select Seats</h4>

                <div className="mb-3 text-center">
                    <span className="btn btn-sm btn-outline-secondary me-2">
                        Available
                    </span>
                    <span className="btn btn-sm btn-success me-2">
                        Selected
                    </span>
                    <span className="btn btn-sm btn-danger">
                        Booked
                    </span>
                </div>

                <div className="d-flex flex-column align-items-center">
                    {sortedRows.map((row) => {
                        const rowSeats = getSortedSeats(groupedSeats[row]);

                        return (
                            <div key={row} className="d-flex align-items-center mb-2">
                                <span className="me-3 fw-bold">{row}</span>

                                {rowSeats.map((seat, index) => (
                                    <React.Fragment key={seat.seatNumber}>
                                        <button
                                            className={`btn btn-sm me-2 ${getSeatClass(seat)}`}
                                            disabled={seat.seatStatus === "BOOKED"}
                                            onClick={() => handleSeatClick(seat)}
                                        >
                                            {seat.seatNumber}
                                        </button>

                                        {index === 2 && <span className="mx-3"></span>}
                                    </React.Fragment>
                                ))}
                            </div>
                        );
                    })}
                </div>

                <div className="mt-4 text-center">
                    <h6>
                        Selected Seats:{" "}
                        {selectedSeats.map((s) => s.seatNumber).join(", ")}
                    </h6>
                </div>

                <div className="text-center mt-3">
                    <button
                        className="btn btn-primary"
                        onClick={handleContinue}
                    >
                        Make Booking
                    </button>
                </div>
            </div>
        </div>
    )
}

export default SeatSelection