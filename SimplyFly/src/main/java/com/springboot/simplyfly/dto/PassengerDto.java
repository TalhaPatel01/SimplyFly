package com.springboot.simplyfly.dto;

public record PassengerDto(
        String name,
        int age,
        String gender,
        String seatNumber
) {
}