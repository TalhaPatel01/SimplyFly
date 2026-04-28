package com.springboot.simplyfly.dto;

public record TopRouteDto(
        String source,
        String destination,
        long totalBookings
) {
}