import { useDispatch } from "react-redux"
import { useParams, useNavigate } from "react-router-dom"
import { useState } from "react"
import { flightUpdate } from "./action/flightUpdateAction"
import OwnerNavbar from "./owner-navbar"

function EditFlight() {

    const { routeFlightId } = useParams()
    const navigate = useNavigate()
    const dispatch = useDispatch()

    const [departureTime, setDepartureTime] = useState("")
    const [arrivalTime, setArrivalTime] = useState("")

    const handleSubmit = async (e) => {
        e.preventDefault()

        await dispatch(flightUpdate(routeFlightId, departureTime, arrivalTime))

        navigate("/owner/flights")
    }

    return (
        <div className="container-fluid bg-light min-vh-100">

            {/* Navbar */}
            <div className="row">
                <OwnerNavbar />
            </div>

            {/* Content */}
            <div className="container mt-4 d-flex justify-content-center">

                <div className="card shadow-sm border-0 rounded-4 p-4" style={{ maxWidth: "500px", width: "100%" }}>

                    <h4 className="fw-bold mb-3 text-center">
                         Edit Flight Time
                    </h4>

                    <form onSubmit={handleSubmit}>

                        {/* Departure Time */}
                        <div className="mb-3">
                            <label className="form-label fw-semibold">
                                Departure Time
                            </label>
                            <input
                                type="time"
                                className="form-control"
                                value={departureTime}
                                onChange={(e) => setDepartureTime(e.target.value)}
                                required
                            />
                        </div>

                        {/* Arrival Time */}
                        <div className="mb-3">
                            <label className="form-label fw-semibold">
                                Arrival Time
                            </label>
                            <input
                                type="time"
                                className="form-control"
                                value={arrivalTime}
                                onChange={(e) => setArrivalTime(e.target.value)}
                                required
                            />
                        </div>

                        {/* Buttons */}
                        <div className="d-flex justify-content-between mt-4">

                            <button
                                type="button"
                                className="btn btn-outline-secondary"
                                onClick={() => navigate("/owner/flights")}
                            >
                                Cancel
                            </button>

                            <button
                                type="submit"
                                className="btn btn-primary"
                            >
                                Update Time
                            </button>

                        </div>

                    </form>
                </div>
            </div>
        </div>
    )
}

export default EditFlight