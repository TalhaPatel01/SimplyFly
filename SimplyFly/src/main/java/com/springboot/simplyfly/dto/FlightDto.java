package com.springboot.simplyfly.dto;

import java.time.LocalTime;

public record FlightDto(
        String airlineName,
        String flightNumber,
        LocalTime departureTime,
        LocalTime arrivalTime
) {
}