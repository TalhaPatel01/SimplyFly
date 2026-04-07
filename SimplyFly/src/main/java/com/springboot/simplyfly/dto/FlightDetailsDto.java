package com.springboot.simplyfly.dto;

import java.time.LocalTime;

public record FlightDetailsDto(
        long routeFlightId,
        String airlineName,
        String flightNumber,
        String sourceCity,
        String destinationCity,
        String sourceAirport,
        String destinationAirport,
        LocalTime arrivalTime,
        LocalTime departureTime,
        int totalSeats,
        int availableSeats,
        int baggageAllowed
) {
}