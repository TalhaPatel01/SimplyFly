package com.springboot.simplyfly.dto;

public record AdminStatDto(
        long totalUsers,
        long totalOwners,
        long totalBookings,
        long totalRoutes
) {
}