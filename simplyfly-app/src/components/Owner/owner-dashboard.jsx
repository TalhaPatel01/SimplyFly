import { useEffect, useState } from "react"
import axios from "axios"
import OwnerNavbar from "./owner-navbar"
import { Chart } from "primereact/chart"
import { useNavigate } from "react-router-dom"

function OwnerDashboard() {
    const navigate = useNavigate()

    const [stats, setStats] = useState({
        totalFlights: 0,
        totalBookings: 0,
        totalRevenue: 0
    })

    const token = localStorage.getItem("token")
    if (!token) {
        navigate("/login")
    }

    const [topRoutes, setTopRoutes] = useState([])

    const fetchStats = async () => {
        try {
            const response = await axios.get("http://localhost:8080/api/owner/stats", {
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("token")
                }
            })
            console.log(response.data)
            setStats(response.data)
        } catch (error) {
            console.log("Error fetching stats", error.message)
        }
    }

    const fetchTopRoutes = async () => {
        try {
            const response = await axios.get("http://localhost:8080/api/owner/top-routes", {
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("token")
                }
            })
            console.log(response.data)
            setTopRoutes(response.data)
        }
        catch (error) {
            console.log("Error fetching top routes", error.message)
        }
    }

    useEffect(() => {
        fetchStats()
        fetchTopRoutes()
    }, [])

    // const chartData = {
    //     labels: ["Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"],
    //     datasets: [
    //         {
    //             label: "Bookings",
    //             data: [10, 20, 15, 30, 25, 40, 35],
    //             fill: false,
    //             tension: 0.4
    //         }
    //     ]
    // }

    return (
        <div className="container-fluid">
            <div className="row">
                <OwnerNavbar />
            </div>

            {/* 🔹 Main Content */}
            <div className="col-md-10 p-4">

                <h4 className="text-primary mb-4">📊 Dashboard</h4>

                {/* Cards */}
                <div className="row g-3 mb-4">

                    <div className="col-md-3">
                        <div className="card shadow border-0 rounded-4 p-3">
                            <p className="text-muted mb-1">Total Bookings</p>
                            <h4>{stats.totalBookings}</h4>
                        </div>
                    </div>

                    <div className="col-md-3">
                        <div className="card shadow border-0 rounded-4 p-3">
                            <p className="text-muted mb-1">Revenue</p>
                            <h4 className="text-success">₹{stats.totalRevenue}</h4>
                        </div>
                    </div>

                    {/* 🔹 Replaced Cancelled → Active Flights */}
                    <div className="col-md-3">
                        <div className="card shadow border-0 rounded-4 p-3">
                            <p className="text-muted mb-1">Active Flights</p>
                            <h4>{stats.totalFlights}</h4>
                        </div>
                    </div>
                </div>

                {/* Chart
                <div className="card shadow border-0 rounded-4 p-4 mb-4">
                    <h5 className="text-primary mb-3">📈 Booking Trends</h5>
                    <Chart type="line" data={chartData} />
                </div> */}

                {/* Table */}
                <div className="card shadow border-0 rounded-4 p-4">
                    <h5 className="text-primary mb-3">🌍 Top Routes</h5>

                    <div className="table-responsive">
                        <table className="table table-hover">

                            <thead className="table-light">
                                <tr>
                                    <th>Source</th>
                                    <th>Destination</th>
                                    <th>Bookings</th>
                                </tr>
                            </thead>

                            <tbody>
                                {topRoutes.map((r, i) => (
                                    <tr key={i}>
                                        <td>{r.source}</td>
                                        <td>{r.destination}</td>
                                        <td className="fw-bold">{r.totalBookings}</td>
                                    </tr>
                                ))}
                            </tbody>

                        </table>
                    </div>
                </div>

            </div>
        </div>
    )
}

export default OwnerDashboard