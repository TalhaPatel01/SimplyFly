package com.springboot.simplyfly.dto;

import com.springboot.simplyfly.enums.PassengerType;
import com.springboot.simplyfly.enums.SeatClass;

public record PassengerDto(
        String name,
        int age,
        String gender,
        PassengerType passengerType,
        String seatNumber
) {
}