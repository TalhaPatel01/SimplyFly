package com.springboot.simplyfly.dto;

public record OwnerStatResDto(
        long totalFlights,
        long totalBookings,
        long totalRevenue
) {
}