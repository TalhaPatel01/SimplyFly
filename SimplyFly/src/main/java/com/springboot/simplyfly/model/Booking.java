package com.springboot.simplyfly.model;

import com.springboot.simplyfly.enums.BookingStatus;
import com.springboot.simplyfly.enums.SeatClass;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "route_flight_id")
    private RouteFlight flight;

    @Column(name = "booking_date")
    private LocalDate bookingDate;

    @Column(name = "travel_date")
    private LocalDate travelDate;

    @Column(name = "arrival_time")
    private LocalTime arrivalTime;

    @Column(name = "departure_time")
    private LocalTime departureTime;

    @Column(name="seats_booked")
    private int seatsBooked;

    @Column(name="seat_class")
    @Enumerated(EnumType.STRING)
    private SeatClass seatClass;

    @Column(name = "total_amount")
    private double totalAmount;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;
}