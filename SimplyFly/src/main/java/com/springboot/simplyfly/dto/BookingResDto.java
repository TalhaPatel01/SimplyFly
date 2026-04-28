package com.springboot.simplyfly.dto;

import com.springboot.simplyfly.enums.BookingStatus;

import java.util.List;

public record BookingResDto(
        long bookingId,
        BookingStatus status,
        double totalAmount,
        List<PassengerDetailsDto> list
) {
}