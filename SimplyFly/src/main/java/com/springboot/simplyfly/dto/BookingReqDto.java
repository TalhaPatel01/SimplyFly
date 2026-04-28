package com.springboot.simplyfly.dto;

import com.springboot.simplyfly.enums.SeatClass;

import java.time.LocalDate;
import java.util.List;

public record BookingReqDto(
        long routeFlightId,
        LocalDate travelDate,
        SeatClass seatClass,
        List<PassengerDto> passengerList
) {
}