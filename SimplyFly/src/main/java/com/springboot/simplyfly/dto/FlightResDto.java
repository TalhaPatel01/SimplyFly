package com.springboot.simplyfly.dto;

public record FlightResDto(
        String airlineName,
        String flightNumber,
        int totalSeats,
        int baggageAllowed
) {
}