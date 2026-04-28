package com.springboot.simplyfly.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RouteFlight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private LocalDate date;

    @Column(name = "arrival_time")
    private LocalTime arrivalTime;

    @Column(name = "departure_time")
    private LocalTime departureTime;

    @Column(name = "available_seats")
    private int availableSeats;

    private int totalRows;
    private int firstClassRows;
    private int businessClassRows;
    private int premiumEconomyRows;
    private int economyRows;
    private String columns;

    private int duration;

    private double fare;
    private double premiumEconomyFare;
    private double businessClassFare;
    private double firstClassFare;

    private double childFactor;
    private double infantFactor;

    @ManyToOne
    @JoinColumn(name = "route_id")
    private Route route;

    @ManyToOne
    @JoinColumn(name = "flight_id")
    private Flight flight;
}