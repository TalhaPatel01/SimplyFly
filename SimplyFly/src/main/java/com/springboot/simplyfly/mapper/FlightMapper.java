package com.springboot.simplyfly.mapper;

import com.springboot.simplyfly.dto.FlightResDto;
import com.springboot.simplyfly.model.Flight;

public class FlightMapper {

    public static FlightResDto mapToDto(Flight flight){
        return new FlightResDto(
                flight.getOwner().getAirline_name(),
                flight.getFlightNumber(),
                flight.getTotalSeats(),
                flight.getBaggageAllowed()
        );
    }
}