import { useEffect, useState } from "react"
import axios from "axios"
import AdminNavbar from "./admin-navbar" 

function UserList() {

    const [users, setUsers] = useState([])
    const [page, setPage] = useState(0)
    const [totalPages, setTotalPages] = useState(0)

    const size = 5

    useEffect(() => {
        fetchUsers()
    }, [page])

    const fetchUsers = async () => {
        try {
            const config = {
                headers: {
                    "Authorization": "Bearer " + localStorage.getItem("token")
                }
            }

            const res = await axios.get(
                `http://localhost:8080/api/user/get-all?page=${page}&size=${size}`,
                config
            )

            setUsers(res.data.list)
            setTotalPages(res.data.totalPages)

        } catch (err) {
            console.log(err.message)
        }
    }

    return (
        <div className="container-fluid bg-light min-vh-100">
            
            {/* Navbar */}
            <div className="row">
                <AdminNavbar/>
            </div>

            <div className="container mt-4">

                {/* Header */}
                <div className="d-flex justify-content-between align-items-center mb-3">
                    <h3 className="fw-bold">👤 Users</h3>
                </div>

                {/* Table */}
                <div className="card shadow-sm border-0 rounded-4">
                    <div className="card-body">

                        <table className="table table-hover align-middle">
                            <thead className="table-light">
                                <tr>
                                    <th>ID</th>
                                    <th>Username</th>
                                    <th>Name</th>
                                    <th>Email</th>
                                    <th>Phone</th>
                                </tr>
                            </thead>

                            <tbody>
                                {users.length > 0 ? (
                                    users.map((u) => (
                                        <tr key={u.id}>
                                            <td>{u.id}</td>
                                            <td>{u.username}</td>
                                            <td>{u.name}</td>
                                            <td>{u.email}</td>
                                            <td>{u.phone_no}</td>
                                        </tr>
                                    ))
                                ) : (
                                    <tr>
                                        <td colSpan="5" className="text-center text-muted">
                                            No Users Found
                                        </td>
                                    </tr>
                                )}
                            </tbody>
                        </table>

                        {/* Pagination */}
                        <div className="d-flex justify-content-between align-items-center mt-3">

                            <button 
                                className="btn btn-outline-secondary"
                                disabled={page === 0}
                                onClick={() => setPage(page - 1)}
                            >
                                Previous
                            </button>

                            <span className="fw-semibold">
                                Page {page + 1} of {totalPages}
                            </span>

                            <button 
                                className="btn btn-outline-secondary"
                                disabled={page === totalPages - 1 || totalPages === 0}
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

export default UserList