import axios from "axios"
import AdminNavbar from "./admin-navbar"
import { useEffect, useState } from "react"
import { useNavigate } from "react-router-dom"

function AdminDashboard() {
    const navigate = useNavigate()
    const token = localStorage.getItem("token")
    if (!token) {
        navigate("/login")
    }

    const [stats, setStats] = useState({
        totalUsers: 0,
        totalOwners: 0,
        totalBookings: 0,
        totalRoutes: 0
    })

    const fetchStats = async () => {
        try {
            const response = await axios.get("http://localhost:8080/api/admin/stats", {
                headers: {
                    Authorization: "Bearer " + token
                }
            })
            setStats(response.data)
            console.log(response.data)
        }
        catch (err) {
            console.log(err.message)
        }
    }

    useEffect(() => {
        fetchStats()
    }, [])

    return (
        <div className="container-fluid">
            <div className="row">
                <AdminNavbar />
            </div>

            {/* Cards */}
            <div className="col-md-10 p-4">

                <h4 className="text-primary mb-4">📊 Dashboard</h4>
                <div className="row g-3 mb-4">

                    <div className="col-md-3">
                        <div className="card shadow border-0 rounded-4 p-3">
                            <p className="text-muted mb-1">Total Users</p>
                            <h4>{stats.totalUsers}</h4>
                        </div>
                    </div>

                    <div className="col-md-3">
                        <div className="card shadow border-0 rounded-4 p-3">
                            <p className="text-muted mb-1">Total Owners</p>
                            <h4>{stats.totalOwners}</h4>
                        </div>
                    </div>

                    <div className="col-md-3">
                        <div className="card shadow border-0 rounded-4 p-3">
                            <p className="text-muted mb-1">Total Bookings</p>
                            <h4>{stats.totalBookings}</h4>
                        </div>
                    </div>

                    <div className="col-md-3">
                        <div className="card shadow border-0 rounded-4 p-3">
                            <p className="text-muted mb-1">Total Routes</p>
                            <h4>{stats.totalRoutes}</h4>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default AdminDashboard