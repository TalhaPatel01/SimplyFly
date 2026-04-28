package com.springboot.simplyfly.dto;

import com.springboot.simplyfly.enums.SeatClass;

import java.time.LocalDate;

public record RouteFlightSearchReqDto(
        String source,
        String destination,
        LocalDate date,
        SeatClass seatClass
) {
}