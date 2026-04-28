package com.springboot.simplyfly.dto;

import java.util.List;

public record BookingFullResDto(
        long bookingId,
        String bookingStatus,
        double totalAmount,
        String source,
        String destination,
        String travelDate,
        FlightDto flight,
        List<PassengerDto> passengers
) {
}