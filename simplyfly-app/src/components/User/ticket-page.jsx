import React, { useEffect, useState, useRef } from "react";
import axios from "axios";
import { useParams } from "react-router-dom";
import jsPDF from "jspdf";
import html2canvas from "html2canvas";

const TicketPage = () => {
    const { bookingId } = useParams();
    const [ticket, setTicket] = useState(null);

    const ticketRef = useRef();

    useEffect(() => {
        const fetchTicket = async () => {
            try {
                const config = {
                    headers: {
                        Authorization: "Bearer " + localStorage.getItem("token"),
                    },
                };

                const res = await axios.get(
                    `http://localhost:8080/api/ticket/download/${bookingId}`,
                    config
                );

                setTicket(res.data);
            } catch (err) {
                console.log(err);
            }
        };

        fetchTicket();
    }, [bookingId]);

    const downloadPDF = async () => {
        const canvas = await html2canvas(ticketRef.current, {
            scale: 3,
        });

        const imgData = canvas.toDataURL("image/png");

        const pdf = new jsPDF("p", "mm", "a4");

        const imgWidth = 210;
        const imgHeight = (canvas.height * imgWidth) / canvas.width;

        pdf.addImage(imgData, "PNG", 0, 10, imgWidth, imgHeight);
        pdf.save(`SimplyFly_Ticket_${ticket.bookingId}.pdf`);
    };

    if (!ticket) {
        return <div className="text-center mt-5">Loading ticket...</div>;
    }

    return (
        <div className="container mt-4">

            <div ref={ticketRef} className="bg-white p-4">

                <div className="card shadow-lg border-0 rounded-4 overflow-hidden">

                    <div className="text-center py-4 bg-light border-bottom">
                        <h2 className="fw-bold text-primary mb-1">
                            ✈ SimplyFly
                        </h2>
                        <small className="text-muted">
                            Your Journey, Our Priority
                        </small>
                    </div>

                    <div className="d-flex justify-content-between align-items-center p-4">
                        <div>
                            <h5 className="fw-bold mb-0">
                                {ticket.airlineName}
                            </h5>
                            <small className="text-muted">
                                Flight {ticket.flightNumber}
                            </small>
                        </div>

                        <div className="text-end">
                            <span className="badge bg-primary px-3 py-2">
                                {ticket.seatClass}
                            </span>
                            <p className="mb-0 text-muted small mt-1">
                                {ticket.travelDate}
                            </p>
                        </div>
                    </div>

                    <hr className="m-0" />

                    <div className="text-center p-4">
                        <h3 className="fw-bold">
                            {ticket.sourceCity} → {ticket.destinationCity}
                        </h3>
                        <p className="text-muted mb-0">
                            {ticket.sourceAirport} → {ticket.destinationAirport}
                        </p>
                    </div>

                    <div className="d-flex justify-content-between text-center px-5 pb-4">
                        <div>
                            <small className="text-muted">Departure</small>
                            <h5 className="fw-bold">
                                {ticket.departureTime}
                            </h5>
                        </div>

                        <div className="align-self-center fs-4">✈</div>

                        <div>
                            <small className="text-muted">Arrival</small>
                            <h5 className="fw-bold">
                                {ticket.arrivalTime}
                            </h5>
                        </div>
                    </div>

                    <hr style={{ borderTop: "2px dashed #ccc" }} />

                    <div className="px-4 pb-4">
                        <h6 className="fw-bold mb-3">
                            Passenger Details
                        </h6>

                        {ticket.passengers.map((p, i) => (
                            <div
                                key={i}
                                className="d-flex justify-content-between border-bottom py-2"
                            >
                                <div>
                                    <strong>{p.name}</strong>
                                    <br />
                                    <small className="text-muted">
                                        {p.passengerType} | {p.gender} | {p.age}
                                    </small>
                                </div>

                                <div className="text-end">
                                    <small className="text-muted">Seat</small>
                                    <div className="fw-bold">
                                        {p.seatNumber || "N/A"}
                                    </div>
                                </div>
                            </div>
                        ))}
                    </div>

                    <hr />

                    <div className="px-4 pb-4 d-flex justify-content-between">

                        <div>
                            <small className="text-muted">Baggage</small>
                            <div className="fw-semibold">
                                {ticket.baggageAllowed} kg
                            </div>
                        </div>

                        <div>
                            <small className="text-muted">Hand Carry</small>
                            <div className="fw-semibold">
                                {ticket.handCarryAllowed} kg
                            </div>
                        </div>

                        <div>
                            <small className="text-muted">Fare</small>
                            <div className="fw-semibold">
                                ₹{ticket.totalAmount}
                            </div>
                        </div>
                    </div>

                    <hr />

                    <div className="text-center py-3 bg-light">
                        <small className="text-muted">
                            Booking ID: {ticket.bookingId} | Booked on{" "}
                            {ticket.bookingDate}
                        </small>
                    </div>

                </div>
            </div>

            <div className="text-center mt-4">
                <button
                    className="btn btn-success px-4 py-2 rounded-3 shadow"
                    onClick={downloadPDF}
                >
                    Download Ticket (PDF)
                </button>
            </div>

        </div>
    );
};

export default TicketPage;