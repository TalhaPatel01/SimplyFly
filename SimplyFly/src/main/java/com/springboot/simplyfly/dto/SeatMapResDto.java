package com.springboot.simplyfly.dto;

import java.util.List;

public record SeatMapResDto(
        List<SeatResDto> list,
        long totalSeats,
        long bookedSeats,
        long availableSeats
) {
}