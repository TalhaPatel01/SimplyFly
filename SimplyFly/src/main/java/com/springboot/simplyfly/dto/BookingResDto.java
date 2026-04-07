package com.springboot.simplyfly.dto;

import com.springboot.simplyfly.enums.BookingStatus;

public record BookingResDto(
        long bookingId,
        BookingStatus status,
        double totalAmount
) {
}