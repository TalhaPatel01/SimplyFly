import { useEffect, useState, useRef } from "react"
import axios from "axios"
import AdminNavbar from "./admin-navbar"

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
        onSelect(airport.code)
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
                <ul className="list-group"
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
                            <span className={`badge ${i === activeIdx ? "bg-light text-primary" : "bg-primary bg-opacity-10 text-primary"}`}>
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

function RouteList() {

    const [routes, setRoutes] = useState([])
    const [airports, setAirports] = useState([])

    const [page, setPage] = useState(0)
    const [totalPages, setTotalPages] = useState(0)

    const [showForm, setShowForm] = useState(false)

    const [sourceCode, setSourceCode] = useState("")
    const [destinationCode, setDestinationCode] = useState("")
    const [distance, setDistance] = useState("")

    const size = 5

    useEffect(() => {
        fetchRoutes()
    }, [page])

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

    const fetchRoutes = async () => {
        try {
            const config = {
                headers: {
                    "Authorization": "Bearer " + localStorage.getItem("token")
                }
            }

            const res = await axios.get(
                `http://localhost:8080/api/route/get-all?page=${page}&size=${size}`,
                config
            )

            setRoutes(res.data.list)
            setTotalPages(res.data.totalPages)

        } catch (err) {
            console.log(err.message)
        }
    }

    const addRoute = async () => {
        try {
            const config = {
                headers: {
                    "Authorization": "Bearer " + localStorage.getItem("token")
                }
            }

            await axios.post(
                "http://localhost:8080/api/route/add-route",
                {
                    sourceCode,
                    destinationCode,
                    distance: Number(distance)
                },
                config
            )

            setSourceCode("")
            setDestinationCode("")
            setDistance("")
            setShowForm(false)

            fetchRoutes()

        } catch (err) {
            console.log(err.message)
        }
    }

    return (
        <div className="container-fluid bg-light min-vh-100">

            {/* Navbar */}
            <div className="row">
                <AdminNavbar />
            </div>

            <div className="container mt-4">

                {/* Header */}
                <div className="d-flex justify-content-between align-items-center mb-3">
                    <h3 className="fw-bold">🌍 Routes</h3>

                    <button
                        className="btn btn-primary"
                        onClick={() => setShowForm(!showForm)}
                    >
                        + Add Route
                    </button>
                </div>

                {/* Add Form */}
                {showForm && (
                    <div className="card p-3 mb-3 shadow-sm">
                        <div className="row g-2 align-items-end">

                            <div className="col-md-4">
                                <AirportInput
                                    label="Source Airport"
                                    placeholder="Search source..."
                                    airports={airports}
                                    onSelect={setSourceCode}
                                />
                            </div>

                            <div className="col-md-4">
                                <AirportInput
                                    label="Destination Airport"
                                    placeholder="Search destination..."
                                    airports={airports}
                                    onSelect={setDestinationCode}
                                />
                            </div>

                            <div className="col-md-3">
                                <input
                                    className="form-control"
                                    placeholder="Distance"
                                    value={distance}
                                    onChange={(e) => setDistance(e.target.value)}
                                />
                            </div>

                            <div className="col-md-1 d-flex align-items-end">
                                <button className="btn btn-success w-100" onClick={addRoute}>
                                    Save
                                </button>
                            </div>

                        </div>
                    </div>
                )}

                {/* Table */}
                <div className="card shadow-sm border-0 rounded-4">
                    <div className="card-body">

                        <table className="table table-hover align-middle">
                            <thead className="table-light">
                                <tr>
                                    <th>ID</th>
                                    <th>Source</th>
                                    <th>Destination</th>
                                    <th>Distance</th>
                                </tr>
                            </thead>

                            <tbody>
                                {routes.length > 0 ? (
                                    routes.map((r) => (
                                        <tr key={r.id}>
                                            <td>{r.id}</td>
                                            <td>{r.source}</td>
                                            <td>{r.destination}</td>
                                            <td>{r.distance}</td>
                                        </tr>
                                    ))
                                ) : (
                                    <tr>
                                        <td colSpan="4" className="text-center text-muted">
                                            No Routes Found
                                        </td>
                                    </tr>
                                )}
                            </tbody>
                        </table>

                        {/* Pagination */}
                        <div className="d-flex justify-content-between mt-3">

                            <button
                                className="btn btn-outline-secondary"
                                disabled={page === 0}
                                onClick={() => setPage(page - 1)}
                            >
                                Previous
                            </button>

                            <span>Page {page + 1} of {totalPages}</span>

                            <button
                                className="btn btn-outline-secondary"
                                disabled={page === totalPages - 1}
                                onClick={() => setPage(page + 1)}
                            >
                                Next
                            </button>

                        </div>

                    </div>
                </div>

            </div>
        </div>
    )
}

export default RouteList