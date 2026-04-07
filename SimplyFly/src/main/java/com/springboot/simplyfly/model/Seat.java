package com.springboot.simplyfly.model;

import com.springboot.simplyfly.enums.SeatStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "seat_number")
    private String seatNumber;

    @Enumerated(EnumType.STRING)
    private SeatStatus seatStatus;

    @ManyToOne
    @JoinColumn(name = "route_flight_id")
    private RouteFlight routeFlight;
}