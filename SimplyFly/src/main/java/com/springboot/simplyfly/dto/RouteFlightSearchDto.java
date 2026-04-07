package com.springboot.simplyfly.dto;

import java.time.LocalTime;

public record RouteFlightSearchDto(
        long routeFlightId,
        String source,
        String destination,
        String flightNumber,
        String airlineName,
        LocalTime arrivalTime,
        int duration,
        LocalTime departureTime,
        double fare
) {
}