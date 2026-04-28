package com.springboot.simplyfly.dto;

import java.time.LocalTime;

public record FlightUpdateDto(
        LocalTime departureTime,
        LocalTime arrivalTime
) {
}