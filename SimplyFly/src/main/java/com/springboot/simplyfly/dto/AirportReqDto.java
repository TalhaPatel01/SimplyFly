package com.springboot.simplyfly.dto;

public record AirportReqDto(
        String name,
        String code,
        String city,
        String country
) {
}