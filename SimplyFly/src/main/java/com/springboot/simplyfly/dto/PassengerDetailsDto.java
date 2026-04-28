package com.springboot.simplyfly.dto;

public record PassengerDetailsDto(
        long id,
        String name,
        int age,
        String gender,
        String passengerType,
        String seatNumber,
        String seatClass
) {
}