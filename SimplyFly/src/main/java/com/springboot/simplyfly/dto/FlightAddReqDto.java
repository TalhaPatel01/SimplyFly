package com.springboot.simplyfly.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record FlightAddReqDto(
        String flightNumber,
        String sourceAirportCode,
        String destinationAirportCode,
        LocalDate date,
        LocalTime arrivalTime,
        LocalTime departureTime,
        int baggageAllowed,
        int handCarryAllowed,
        int economyRows,
        int premiumEconomyRows,
        int businessClassRows,
        int firstClassRows,
        double economyFare,
        double premiumEconomyFare,
        double businessClassFare,
        double firstClassFare,
        double infantFactor,
        double childFactor
) {
}