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
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long route_id;

    @ManyToOne
    @JoinColumn(name="source_airport_id",nullable = false)
    private Airport sourceAirport;

    @ManyToOne
    @JoinColumn(name="destination_airport_id",nullable = false)
    private Airport destinationAirport;

    private double distance;
}