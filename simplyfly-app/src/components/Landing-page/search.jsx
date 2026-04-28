import axios from "axios"
import { useState, useRef, useEffect } from "react"
import { useNavigate } from "react-router-dom"

function AirportInput({ label, placeholder, onSelect, airports }) {
    const [query, setQuery] = useState("")
    const [results, setResults] = useState([])
    const [show, setShow] = useState(false)
    const [activeIdx, setActiveIdx] = useState(-1)
    const wrapRef = useRef(null)

    useEffect(() => {
        const handler = (e) => {
            if (wrapRef.current && !wrapRef.current.contains(e.target)) setShow(false)
        }
        document.addEventListener("mousedown", handler)
        return () => document.removeEventListener("mousedown", handler)
    }, [])

    const handleChange = (e) => {
        const q = e.target.value
        setQuery(q)
        onSelect(undefined)
        if (!q) { setResults([]); setShow(false); return }
        const ql = q.toLowerCase()
        const filtered = airports.filter(a =>
            a.city.toLowerCase().startsWith(ql) ||
            a.code.toLowerCase().startsWith(ql) ||
            a.name.toLowerCase().includes(ql)
        ).slice(0, 6)
        setResults(filtered)
        setShow(filtered.length > 0)
        setActiveIdx(-1)
    }

    const handleSelect = (airport) => {
        setQuery(`${airport.city} (${airport.code})`)
        onSelect(airport.city)
        setShow(false)
        setResults([])
    }

    const handleKeyDown = (e) => {
        if (!show) return
        if (e.key === "ArrowDown") { e.preventDefault(); setActiveIdx(i => Math.min(i + 1, results.length - 1)) }
        else if (e.key === "ArrowUp") { e.preventDefault(); setActiveIdx(i => Math.max(i - 1, 0)) }
        else if (e.key === "Enter" && activeIdx >= 0) { e.preventDefault(); handleSelect(results[activeIdx]) }
        else if (e.key === "Escape") setShow(false)
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
                <ul className="list-group" style={{
                    position: "absolute", top: "100%", left: 0, right: 0,
                    zIndex: 1000, marginTop: "2px",
                    boxShadow: "0 4px 12px rgba(0,0,0,0.1)",
                    borderRadius: "8px", overflow: "hidden"
                }}>
                    {results.map((airport, i) => (
                        <li
                            key={airport.code}
                            className={`list-group-item list-group-item-action d-flex align-items-center gap-2 ${i === activeIdx ? "active" : ""}`}
                            style={{ cursor: "pointer" }}
                            onMouseDown={() => handleSelect(airport)}
                            onMouseEnter={() => setActiveIdx(i)}
                        >
                            <span className={`badge ${i === activeIdx ? "bg-light text-primary" : "bg-primary bg-opacity-10 text-primary"}`}
                                style={{ fontSize: "11px", minWidth: "42px" }}>
                                {airport.code}
                            </span>
                            <div>
                                <div style={{ fontWeight: 500, fontSize: "14px" }}>{airport.city}</div>
                                <div style={{ fontSize: "12px", opacity: 0.7 }}>{airport.name}</div>
                            </div>
                        </li>
                    ))}
                </ul>
            )}
        </div>
    )
}

function Search() {
    const [airports, setAirports] = useState([])
    const [source, setSource] = useState(undefined)
    const [destination, setDestination] = useState(undefined)
    const [travelDate, setTravelDate] = useState(undefined)
    const [seatClass, setSeatClass] = useState("ECONOMY")
    const [errMsg, setErrMsg] = useState(undefined)
    const navigate = useNavigate()

    useEffect(() => {
        const fetchAirports = async () => {
            try {
                const response = await axios.get("http://localhost:8080/api/airport/get-all")
                setAirports(response.data)
            }
            catch (err) {
                console.log(err)
            }
        }
        fetchAirports()
    }, [])

    const searchFlights = async (e) => {
        e.preventDefault()

        if (!source || !destination || !travelDate || !seatClass) {
            setErrMsg("Please fill all details")
            return
        }

        try {
            const response = await axios.post(
                "http://localhost:8080/api/route/flight/search",
                {
                    source,
                    destination,
                    date: travelDate,
                    seatClass
                }
            )
            navigate("/search-results",
                {
                    state:
                    {
                        flights: response.data,
                        source,
                        destination,
                        travelDate,
                        seatClass
                    }
                })
        } catch (err) {
            setErrMsg(err.message)
        }
    }

    return (
        <div className="container-fluid">
            <div className="row mt-4 justify-content-center">
                <div className="col-lg-12">
                    <div className="card border-0 shadow-sm rounded-3">
                        <div className="card-body">

                            <h4 className="fw-bold text-primary mb-3 text-center">Search Flights</h4>
                            <form onSubmit={(e) => searchFlights(e)}>
                                {errMsg && <div className="alert alert-danger mt-4">{errMsg}</div>}
                                <div className="row g-3 align-items-end">

                                    <div className="col-md-3">
                                        <AirportInput label="Source" placeholder="From City" onSelect={setSource} airports={airports} />
                                    </div>

                                    <div className="col-md-3">
                                        <AirportInput label="Destination" placeholder="To City" onSelect={setDestination} airports={airports} />
                                    </div>

                                    <div className="col-md-3">
                                        <label className="form-label small">Travel Date</label>
                                        <input type="date" className="form-control" onChange={(e) => setTravelDate(e.target.value)} />
                                    </div>

                                    <div className="col-md-3">
                                        <label className="form-label small">Seat Class</label>
                                        <select
                                            className="form-control"
                                            value={seatClass}
                                            onChange={(e) => setSeatClass(e.target.value)}
                                        >
                                            <option value="" disabled>Select Seat Class</option>
                                            <option value="ECONOMY">Economy</option>
                                            <option value="PREMIUM_ECONOMY">Premium Economy</option>
                                            <option value="BUSINESS">Business</option>
                                            <option value="FIRST_CLASS">First Class</option>
                                        </select>
                                    </div>

                                    <div className="col-md-12 d-flex justify-content-center">
                                        <button className="btn btn-primary" type="submit">Search Flights</button>
                                    </div>

                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default Search