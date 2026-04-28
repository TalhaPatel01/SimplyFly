import React, { useEffect, useState } from "react";
import axios from "axios";
import { useParams, useNavigate } from "react-router-dom";
import UserNavbar from "./user-navbar";

const PaymentById = () => {
    const { bookingId } = useParams();
    const navigate = useNavigate();

    const [booking, setBooking] = useState(null);
    const [flight, setFlight] = useState(null);
    const [passengers, setPassengers] = useState([]);
    const [paymentMethod, setPaymentMethod] = useState("");

    useEffect(() => {
        const fetchBooking = async () => {
            try {
                const config = {
                    headers: {
                        Authorization: "Bearer " + localStorage.getItem("token"),
                    },
                };

                const res = await axios.get(
                    `http://localhost:8080/api/booking/${bookingId}`,
                    config
                );

                const data = res.data;

                setBooking(data);
                setFlight(data.flight);
                setPassengers(data.passengers);

            } catch (err) {
                console.log(err);
            }
        };

        fetchBooking();
    }, [bookingId]);

    const handlePayment = async () => {
        if (!paymentMethod) {
            alert("Select payment method");
            return;
        }

        try {
            const config = {
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("token"),
                },
            };

            const res = await axios.post(
                "http://localhost:8080/api/payment/make-payment",
                {
                    bookingId: booking.bookingId,
                    paymentMethod: paymentMethod,
                },
                config
            );

            navigate("/payment-success", {
                state: res.data,
            });

        } catch (err) {
            console.log(err);
            alert("Payment failed");
        }
    };

    if (!booking) return <div className="text-center mt-5">Loading...</div>;

    return (
        <div className="container-fluid">
            <UserNavbar/>
            <div className="container mt-4">

                {/* HEADER */}
                <div className="text-center mb-4">
                    <h3 className="fw-bold text-primary">Complete Your Payment</h3>
                    <p className="text-muted small">Secure checkout for your trip</p>
                </div>

                {/* BOOKING SUMMARY */}
                <div className="card border-0 shadow-sm rounded-4 mb-3">
                    <div className="card-body d-flex justify-content-between align-items-center">
                        <div>
                            <h6 className="fw-bold text-primary mb-1">
                                Booking ID: {booking.bookingId}
                            </h6>
                            <small className="text-muted">
                                Status: {booking.bookingStatus}
                            </small>
                        </div>
                        <div className="text-end">
                            <h5 className="fw-bold text-success mb-0">
                                ₹{booking.totalAmount}
                            </h5>
                        </div>
                    </div>
                </div>

                {/* FLIGHT CARD */}
                <div className="card border-0 shadow-sm rounded-4 mb-3">
                    <div className="card-body d-flex justify-content-between align-items-center">
                        <div>
                            <h6 className="fw-bold text-primary mb-1">
                                {booking.source} → {booking.destination}
                            </h6>
                            <small className="text-muted">
                                {flight?.airlineName}
                            </small>
                        </div>

                        <div className="text-end">
                            <p className="mb-0 fw-semibold">
                                {flight?.flightNumber}
                            </p>
                            <small className="text-muted">
                                Economy
                            </small>
                        </div>
                    </div>
                </div>

                {/* PASSENGERS */}
                <div className="card border-0 shadow-sm rounded-4 mb-3">
                    <div className="card-body">
                        <h6 className="fw-bold mb-3">Passengers</h6>

                        {passengers.map((p, i) => (
                            <div
                                key={i}
                                className="d-flex justify-content-between align-items-center border-bottom py-2"
                            >
                                <span className="fw-medium">{p.name}</span>
                                <span className="text-muted small">
                                    Seat: {p.seatNumber}
                                </span>
                            </div>
                        ))}
                    </div>
                </div>

                {/* PAYMENT OPTIONS */}
                <div className="card border-0 shadow-sm rounded-4 mb-3">
                    <div className="card-body">
                        <h6 className="fw-bold mb-3">Select Payment Method</h6>

                        <div className="form-check mb-2">
                            <input
                                className="form-check-input"
                                type="radio"
                                name="payment"
                                value="UPI"
                                onChange={(e) => setPaymentMethod(e.target.value)}
                            />
                            <label className="form-check-label">
                                UPI
                            </label>
                        </div>

                        <div className="form-check mb-2">
                            <input
                                className="form-check-input"
                                type="radio"
                                name="payment"
                                value="CARD"
                                onChange={(e) => setPaymentMethod(e.target.value)}
                            />
                            <label className="form-check-label">
                                Credit / Debit Card
                            </label>
                        </div>

                        <div className="form-check">
                            <input
                                className="form-check-input"
                                type="radio"
                                name="payment"
                                value="NETBANKING"
                                onChange={(e) => setPaymentMethod(e.target.value)}
                            />
                            <label className="form-check-label">
                                Net Banking
                            </label>
                        </div>
                    </div>
                </div>

                {/* PAY BUTTON */}
                <div className="text-center">
                    <button
                        className="btn btn-primary px-5 py-2 rounded-3 fw-semibold shadow-sm"
                        onClick={handlePayment}
                    >
                        Pay ₹{booking.totalAmount}
                    </button>
                </div>

            </div>
        </div>
    );
};

export default PaymentById;