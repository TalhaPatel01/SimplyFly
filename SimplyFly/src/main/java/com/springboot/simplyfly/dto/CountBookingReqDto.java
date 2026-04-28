package com.springboot.simplyfly.dto;

import java.time.LocalDate;

public record CountBookingReqDto(
        String flightNumber,
        LocalDate date
) {
}