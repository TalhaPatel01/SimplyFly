package com.springboot.simplyfly.dto;

public record DocumentResDto(
        String fileName,
        long passengerId,
        String passengerName,
        long bookingId
) {
}