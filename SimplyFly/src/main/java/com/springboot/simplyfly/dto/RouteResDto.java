package com.springboot.simplyfly.dto;

public record RouteResDto(
        long id,
        String source,
        String destination,
        double distance
) {
}