import { useEffect, useState } from "react"
import { useLocation, useParams } from "react-router-dom"
import axios from "axios"
import OwnerNavbar from "./owner-navbar"

function SeatMap() {
    const { routeFlightId } = useParams()
    const location = useLocation()
    const {flightNumber} = location.state || {}

    const [seats, setSeats] = useState([])
    const [loading, setLoading] = useState(true)
    const [error, setError] = useState(null)

    const [summary, setSummary] = useState({
        totalSeats: 0,
        bookedSeats: 0,
        availableSeats: 0
    })

    useEffect(() => {
        const fetchSeatMap = async () => {
            try {
                const token = localStorage.getItem("token")

                const res = await axios.get(
                    `http://localhost:8080/api/seat/get/seat-map/${routeFlightId}`,
                    {
                        headers: {
                            Authorization: `Bearer ${token}`
                        }
                    }
                )

                setSeats(res.data.list)
                setSummary({
                    totalSeats: res.data.totalSeats,
                    bookedSeats: res.data.bookedSeats,
                    availableSeats: res.data.availableSeats
                })

            } catch (err) {
                console.error(err)
                setError("Failed to load seat map")
            } finally {
                setLoading(false)
            }
        }

        fetchSeatMap()
    }, [routeFlightId])

    if (loading) return <h5 className="text-center mt-5">Loading...</h5>
    if (error) return <h5 className="text-danger text-center mt-5">{error}</h5>

    // ✅ GROUP BY CLASS
    const groupedByClass = seats.reduce((acc, seat) => {
        if (!acc[seat.seatClass]) acc[seat.seatClass] = []
        acc[seat.seatClass].push(seat)
        return acc
    }, {})

    // Sort classes (optional order)
    const classOrder = ["FIRST_CLASS", "BUSINESS", "PREMIUM_ECONOMY", "ECONOMY"]

    return (
        <div className="container-fluid bg-light min-vh-100">

            <div className="row">
                <OwnerNavbar />
            </div>

            <div className="container mt-4">

                <div className="card shadow border-0 rounded-4 p-4">

                    {/* Header */}
                    <h3 className="fw-bold text-primary text-center mb-4">
                        ✈️ Seat Map (Flight #{flightNumber})
                    </h3>

                    {/* Summary */}
                    <div className="row text-center mb-4">
                        <div className="col-md-4">
                            <div className="card p-3">
                                <h6>Total Seats</h6>
                                <h5>{summary.totalSeats}</h5>
                            </div>
                        </div>
                        <div className="col-md-4">
                            <div className="card p-3 text-danger">
                                <h6>Booked</h6>
                                <h5>{summary.bookedSeats}</h5>
                            </div>
                        </div>
                        <div className="col-md-4">
                            <div className="card p-3 text-success">
                                <h6>Available</h6>
                                <h5>{summary.availableSeats}</h5>
                            </div>
                        </div>
                    </div>

                    {/* Legend */}
                    <div className="text-center mb-4">
                        <span className="badge bg-success me-2 px-3 py-2">Available</span>
                        <span className="badge bg-danger px-3 py-2">Booked</span>
                    </div>

                    {/* Seat Sections */}
                    {
                        classOrder.map((seatClass) => {

                            const classSeats = groupedByClass[seatClass]
                            if (!classSeats) return null

                            // GROUP BY ROW
                            const groupedByRow = classSeats.reduce((acc, seat) => {
                                if (!acc[seat.row]) acc[seat.row] = []
                                acc[seat.row].push(seat)
                                return acc
                            }, {})

                            const sortedRows = Object.keys(groupedByRow).sort((a, b) => a - b)

                            return (
                                <div key={seatClass} className="mb-5">

                                    {/* Section Title */}
                                    <h5 className="text-primary text-center mb-3">
                                        {seatClass.replaceAll("_", " ")}
                                    </h5>

                                    <div className="d-flex flex-column align-items-center">

                                        {sortedRows.map((row) => {

                                            const rowSeats = groupedByRow[row]
                                                .sort((a, b) => a.column.localeCompare(b.column))

                                            return (
                                                <div key={row} className="d-flex align-items-center mb-2">

                                                    <span className="me-3 fw-bold">{row}</span>

                                                    {rowSeats.map((seat, index) => (

                                                        <div
                                                            key={seat.seatNumber}
                                                            className={`btn btn-sm me-2 ${
                                                                seat.seatStatus === "BOOKED"
                                                                    ? "btn-danger"
                                                                    : "btn-outline-success"
                                                            }`}
                                                            style={{ width: "50px" }}
                                                        >
                                                            {seat.seatNumber}
                                                        </div>

                                                    ))}

                                                </div>
                                            )
                                        })}
                                    </div>
                                </div>
                            )
                        })
                    }

                </div>
            </div>
        </div>
    )
}

export default SeatMap