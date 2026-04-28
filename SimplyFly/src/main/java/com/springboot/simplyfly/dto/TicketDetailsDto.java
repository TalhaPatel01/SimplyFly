package com.springboot.simplyfly.dto;

import com.springboot.simplyfly.enums.SeatClass;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record TicketDetailsDto(
        long bookingId,
        String sourceCity,
        String sourceAirport,
        String destinationCity,
        String destinationAirport,
        LocalTime departureTime,
        LocalTime arrivalTime,
        SeatClass seatClass,
        double totalAmount,
        LocalDate travelDate,
        LocalDate bookingDate,
        String flightNumber,
        String airlineName,
        int baggageAllowed,
        int handCarryAllowed,
        List<PassengerDto> passengers
) {
}