package com.springboot.simplyfly.dto;

import com.springboot.simplyfly.enums.BookingStatus;
import com.springboot.simplyfly.enums.TicketStatus;

import java.time.LocalDate;
import java.time.LocalTime;

public record UserBookingResDto(
        long bookingId,
        String source,
        String destination,
        String airlineName,
        String flightNumber,
        LocalTime departureTime,
        LocalTime arrivalTime,
        LocalDate travelDate,
        int passengerCount,
        BookingStatus bookingStatus
) {
}