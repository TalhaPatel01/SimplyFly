package com.springboot.simplyfly.dto;

import java.time.LocalTime;

public record FlightResDto(
        long routeFlightId,
        String flightNumber,
        String sourceCity,
        String destinationCity,
        String sourceAirport,
        String destinationAirport,
        String sourceAirportCode,
        String destinationAirportCode,
        LocalTime arrivalTime,
        LocalTime departureTime,
        int totalSeats,
        int availableSeats,
        int firstClassSeats,
        int businessClassSeats,
        int premiumEconomySeats,
        int economySeats,
        double economyFare,
        double premiumEconomyFare,
        double businessClassFare,
        double firstClassFare,
        int baggageAllowed,
        int handCarryAllowed
) {
}