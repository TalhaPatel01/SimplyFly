package com.springboot.simplyfly.dto;

import java.time.LocalDate;
import java.util.List;

public record BookingReqDto(
        long routeFlightId,
        LocalDate travelDate,
        List<PassengerDto> passengerList
) {
}