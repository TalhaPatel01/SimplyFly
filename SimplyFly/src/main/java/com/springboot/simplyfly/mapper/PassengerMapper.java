package com.springboot.simplyfly.mapper;

import com.springboot.simplyfly.dto.PassengerDetailsDto;
import com.springboot.simplyfly.model.Passenger;

public class PassengerMapper {

    public static PassengerDetailsDto mapToDto(Passenger passenger){
        return new PassengerDetailsDto(
                passenger.getId(),
                passenger.getName(),
                passenger.getAge(),
                passenger.getGender(),
                passenger.getPassengerType().toString(),

                passenger.getSeat() != null
                        ? passenger.getSeat().getSeatNumber()
                        : "No Seat",

                passenger.getSeat() != null
                        ? passenger.getSeat().getSeatClass().toString()
                        : "No Seat"
        );
    }
}