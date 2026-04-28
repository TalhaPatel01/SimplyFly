import React, { useState } from "react";
import axios from "axios";
import { useLocation, useNavigate } from "react-router-dom";
import UserNavbar from "./user-navbar";

function UploadDocument() {
    const location = useLocation();
    const navigate = useNavigate();

    const {
        booking,
        flight,
        passengers,
        travelDate,
        seatClass,
        source,
        destination
    } = location.state;

    const [files, setFiles] = useState({});
    const [msg, setMsg] = useState("");

    // store file per passengerId
    const handleFileChange = (passengerId, file) => {
        setFiles(prev => ({
            ...prev,
            [passengerId]: file
        }));
    };

    // upload API call
    const uploadFile = async (passengerId) => {
        try {
            const formData = new FormData();
            formData.append("file", files[passengerId]);

            await axios.post(
                `http://localhost:8080/api/document/upload/${passengerId}`,
                formData,
                {
                    headers: {
                        "Content-Type": "multipart/form-data",
                        "Authorization": "Bearer " + localStorage.getItem("token")
                    }
                }
            );

            setMsg(`Uploaded for passenger ${passengerId}`);
        } catch (err) {
            setMsg(err.message);
        }
    };

    const proceedToPayment = () => {
        navigate("/payment", {
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
    };

    return (
        <div className="container-fluid">
            <UserNavbar />

            <div className="container mt-4">

                <h3 className="mb-3">Upload ID Proof</h3>

                {booking?.list?.map((p) => (
                    <div key={p.id} className="card mb-3 p-3 shadow-sm">

                        <div className="d-flex justify-content-between align-items-center">

                            <div>
                                <h5>{p.name}</h5>
                                <small>
                                    Age: {p.age} | Gender: {p.gender} | Seat: {p.seatNumber || "N/A"}
                                </small>
                            </div>

                            <div className="d-flex gap-2 align-items-center">

                                <input
                                    type="file"
                                    onChange={(e) =>
                                        handleFileChange(p.id, e.target.files[0])
                                    }
                                />

                                <button
                                    className="btn btn-primary"
                                    onClick={() => uploadFile(p.id)}
                                    disabled={!files[p.id]}
                                >
                                    Upload
                                </button>

                            </div>

                        </div>
                    </div>
                ))}

                {msg && (
                    <div className="alert alert-info">
                        {msg}
                    </div>
                )}

                <div className="text-center mt-4">
                    <button
                        className="btn btn-success"
                        onClick={proceedToPayment}
                    >
                        Proceed to Payment
                    </button>
                </div>

            </div>
        </div>
    );
}

export default UploadDocument;