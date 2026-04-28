import { useState, useEffect, useRef } from "react"
import axios from "axios"
import OwnerNavbar from "./owner-navbar"
import { useNavigate } from "react-router-dom"

// ✅ Airport Dropdown Component (FINAL)
function AirportInput({ label, placeholder, onSelect, airports }) {
    const [query, setQuery] = useState("")
    const [results, setResults] = useState([])
    const [show, setShow] = useState(false)
    const [activeIdx, setActiveIdx] = useState(-1)
    const wrapRef = useRef(null)

    useEffect(() => {
        const handler = (e) => {
            if (wrapRef.current && !wrapRef.current.contains(e.target)) {
                setShow(false)
            }
        }
        document.addEventListener("mousedown", handler)
        return () => document.removeEventListener("mousedown", handler)
    }, [])

    const handleChange = (e) => {
        const q = e.target.value
        setQuery(q)
        onSelect(undefined)

        if (!q) {
            setResults([])
            setShow(false)
            return
        }

        const ql = q.toLowerCase()

        const filtered = airports.filter(a =>
            a.city.toLowerCase().includes(ql) ||
            a.code.toLowerCase().includes(ql) ||
            a.name.toLowerCase().includes(ql)
        ).slice(0, 6)

        setResults(filtered)
        setShow(filtered.length > 0)
        setActiveIdx(-1)
    }

    const handleSelect = (airport) => {
        setQuery(`${airport.city} (${airport.code})`)
        onSelect(airport.code) // only code
        setShow(false)
        setResults([])
    }

    const handleKeyDown = (e) => {
        if (!show) return

        if (e.key === "ArrowDown") {
            e.preventDefault()
            setActiveIdx(i => Math.min(i + 1, results.length - 1))
        } else if (e.key === "ArrowUp") {
            e.preventDefault()
            setActiveIdx(i => Math.max(i - 1, 0))
        } else if (e.key === "Enter" && activeIdx >= 0) {
            e.preventDefault()
            handleSelect(results[activeIdx])
        } else if (e.key === "Escape") {
            setShow(false)
        }
    }

    return (
        <div ref={wrapRef} style={{ position: "relative" }}>
            <label className="form-label small">{label}</label>

            <input
                type="text"
                className="form-control"
                placeholder={placeholder}
                value={query}
                onChange={handleChange}
                onKeyDown={handleKeyDown}
                onFocus={() => results.length > 0 && setShow(true)}
                autoComplete="off"
            />

            {show && (
                <ul
                    className="list-group"
                    style={{
                        position: "absolute",
                        top: "100%",
                        left: 0,
                        right: 0,
                        zIndex: 1000,
                        marginTop: "2px",
                        boxShadow: "0 4px 12px rgba(0,0,0,0.1)",
                        borderRadius: "8px",
                        overflow: "hidden"
                    }}
                >
                    {results.map((airport, i) => (
                        <li
                            key={airport.code}
                            className={`list-group-item list-group-item-action d-flex align-items-center gap-2 ${i === activeIdx ? "active" : ""}`}
                            style={{ cursor: "pointer" }}
                            onMouseDown={() => handleSelect(airport)}
                            onMouseEnter={() => setActiveIdx(i)}
                        >
                            <span
                                className={`badge ${i === activeIdx
                                        ? "bg-light text-primary"
                                        : "bg-primary bg-opacity-10 text-primary"
                                    }`}
                                style={{ fontSize: "11px", minWidth: "42px" }}
                            >
                                {airport.code}
                            </span>

                            <div>
                                <div style={{ fontWeight: 500, fontSize: "14px" }}>
                                    {airport.city}
                                </div>
                                <div style={{ fontSize: "12px", opacity: 0.7 }}>
                                    {airport.name}
                                </div>
                            </div>
                        </li>
                    ))}
                </ul>
            )}
        </div>
    )
}

function AddFlight() {
    const [airports, setAirports] = useState([])
    const navigate = useNavigate()

    useEffect(() => {
        const fetchAirports = async () => {
            try {
                const res = await axios.get("http://localhost:8080/api/airport/get-all")
                setAirports(res.data)
            } catch (err) {
                console.log(err)
            }
        }
        fetchAirports()
    }, [])

    const [flightNumber, setFlightNumber] = useState("")
    const [sourceAirportCode, setSourceAirportCode] = useState("")
    const [destinationAirportCode, setDestinationAirportCode] = useState("")
    const [date, setDate] = useState("")
    const [arrivalTime, setArrivalTime] = useState("")
    const [departureTime, setDepartureTime] = useState("")
    const [baggageAllowed, setBaggageAllowed] = useState("")
    const [handCarryAllowed, setHandCarryAllowed] = useState("")

    const [economyRows, setEconomyRows] = useState("")
    const [premiumEconomyRows, setPremiumEconomyRows] = useState("")
    const [businessClassRows, setBusinessClassRows] = useState("")
    const [firstClassRows, setFirstClassRows] = useState("")

    const [economyFare, setEconomyFare] = useState("")
    const [premiumEconomyFare, setPremiumEconomyFare] = useState("")
    const [businessClassFare, setBusinessClassFare] = useState("")
    const [firstClassFare, setFirstClassFare] = useState("")

    const [infantFactor, setInfantFactor] = useState("")
    const [childFactor, setChildFactor] = useState("")

    const handleSubmit = async (e) => {
        e.preventDefault()

        if (!flightNumber || !sourceAirportCode || !destinationAirportCode || !date || !arrivalTime || !departureTime
            || !baggageAllowed || !handCarryAllowed || !economyRows || !premiumEconomyRows || !businessClassRows
            || !firstClassRows || !economyFare || !premiumEconomyFare || !businessClassFare || !firstClassFare
            || !infantFactor || !childFactor
        ) {
            alert("Please enter all fields")
            return
        }

        try {
            const token = localStorage.getItem("token")

            const payload = {
                flightNumber,
                sourceAirportCode,
                destinationAirportCode,
                date,
                arrivalTime,
                departureTime,
                baggageAllowed: Number(baggageAllowed),
                handCarryAllowed: Number(handCarryAllowed),

                economyRows: Number(economyRows),
                premiumEconomyRows: Number(premiumEconomyRows),
                businessClassRows: Number(businessClassRows),
                firstClassRows: Number(firstClassRows),

                economyFare: Number(economyFare),
                premiumEconomyFare: Number(premiumEconomyFare),
                businessClassFare: Number(businessClassFare),
                firstClassFare: Number(firstClassFare),

                infantFactor: Number(infantFactor),
                childFactor: Number(childFactor)
            }

            await axios.post(
                "http://localhost:8080/api/route/flight/add-flight",
                payload,
                {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                }
            )

            alert("Flight added successfully ✅")
            navigate("/owner-dashboard")
            

        } catch (err) {
            console.log(err)
            alert("Failed to add flight ❌")
        }
    }

    return (
        <div className="container-fluid bg-light min-vh-100">

            <div className="row">
                <OwnerNavbar />
            </div>

            <div className="container mt-4">
                <div className="card shadow border-0 rounded-4 p-4">

                    <h3 className="fw-bold mb-4">✈️ Add New Flight</h3>

                    <form onSubmit={handleSubmit}>

                        {/* Flight Details */}
                        <h5 className="text-primary mb-3">Flight Details</h5>
                        <div className="row g-3">

                            <div className="col-md-4">
                                <label className="form-label small">Flight Number</label>
                                <input className="form-control" placeholder="Flight Number"
                                    value={flightNumber}
                                    onChange={(e) => setFlightNumber(e.target.value)}
                                />
                            </div>

                            <div className="col-md-4">
                                <AirportInput
                                    label="Source Airport"
                                    placeholder="Search airport..."
                                    airports={airports}
                                    onSelect={setSourceAirportCode}
                                />
                            </div>

                            <div className="col-md-4">
                                <AirportInput
                                    label="Destination Airport"
                                    placeholder="Search airport..."
                                    airports={airports}
                                    onSelect={setDestinationAirportCode}
                                />
                            </div>

                            <div className="col-md-4">
                                <label className="form-label small">Date</label>
                                <input type="date" className="form-control"
                                    value={date}
                                    onChange={(e) => setDate(e.target.value)}
                                />
                            </div>

                            <div className="col-md-4">
                                <label className="form-label small">Departure Time</label>
                                <input type="time" className="form-control"
                                    value={departureTime}
                                    onChange={(e) => setDepartureTime(e.target.value)}
                                />
                            </div>

                            <div className="col-md-4">
                                <label className="form-label small">Arrival Time</label>
                                <input type="time" className="form-control"
                                    value={arrivalTime}
                                    onChange={(e) => setArrivalTime(e.target.value)}
                                />
                            </div>
                        </div>

                        {/* Baggage */}
                        <h5 className="mt-4 text-primary">Baggage</h5>
                        <div className="row g-3">
                            <div className="col-md-6">
                                <input type="number" className="form-control"
                                    placeholder="Baggage Allowed"
                                    value={baggageAllowed}
                                    onChange={(e) => setBaggageAllowed(e.target.value)}
                                />
                            </div>
                            <div className="col-md-6">
                                <input type="number" className="form-control"
                                    placeholder="Hand Carry Allowed"
                                    value={handCarryAllowed}
                                    onChange={(e) => setHandCarryAllowed(e.target.value)}
                                />
                            </div>
                        </div>

                        {/* Seats */}
                        <h5 className="mt-4 text-primary">Seat Configuration</h5>
                        <div className="row g-3">
                            <div className="col-md-3"><input type="number" className="form-control" placeholder="Economy Rows" value={economyRows} onChange={(e) => setEconomyRows(e.target.value)} /></div>
                            <div className="col-md-3"><input type="number" className="form-control" placeholder="Premium Economy Rows" value={premiumEconomyRows} onChange={(e) => setPremiumEconomyRows(e.target.value)} /></div>
                            <div className="col-md-3"><input type="number" className="form-control" placeholder="Business Rows" value={businessClassRows} onChange={(e) => setBusinessClassRows(e.target.value)} /></div>
                            <div className="col-md-3"><input type="number" className="form-control" placeholder="First Class Rows" value={firstClassRows} onChange={(e) => setFirstClassRows(e.target.value)} /></div>
                        </div>

                        {/* Fare */}
                        <h5 className="mt-4 text-primary">Fare</h5>
                        <div className="row g-3">
                            <div className="col-md-3"><input type="number" className="form-control" placeholder="Economy Fare" value={economyFare} onChange={(e) => setEconomyFare(e.target.value)} /></div>
                            <div className="col-md-3"><input type="number" className="form-control" placeholder="Premium Economy Fare" value={premiumEconomyFare} onChange={(e) => setPremiumEconomyFare(e.target.value)} /></div>
                            <div className="col-md-3"><input type="number" className="form-control" placeholder="Business Fare" value={businessClassFare} onChange={(e) => setBusinessClassFare(e.target.value)} /></div>
                            <div className="col-md-3"><input type="number" className="form-control" placeholder="First Class Fare" value={firstClassFare} onChange={(e) => setFirstClassFare(e.target.value)} /></div>
                        </div>

                        {/* Factors */}
                        <h5 className="mt-4 text-primary">Pricing Factors</h5>
                        <div className="row g-3">
                            <div className="col-md-6"><input type="number" step="0.1" className="form-control" placeholder="Infant Factor" value={infantFactor} onChange={(e) => setInfantFactor(e.target.value)} /></div>
                            <div className="col-md-6"><input type="number" step="0.1" className="form-control" placeholder="Child Factor" value={childFactor} onChange={(e) => setChildFactor(e.target.value)} /></div>
                        </div>

                        <div className="mt-4 text-end">
                            <button type="submit" className="btn btn-primary px-4">
                                Add Flight
                            </button>
                        </div>

                    </form>
                </div>
            </div>
        </div>
    )
}

export default AddFlight