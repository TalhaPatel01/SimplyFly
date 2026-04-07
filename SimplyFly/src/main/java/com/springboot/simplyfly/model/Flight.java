package com.springboot.simplyfly.model;

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
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private Owner owner;

    @Column(name = "flight_number")
    private String flightNumber;

    @Column(name = "total_seats")
    private int totalSeats;

    @Column(name = "baggage_allowed")
    private int baggageAllowed;
}