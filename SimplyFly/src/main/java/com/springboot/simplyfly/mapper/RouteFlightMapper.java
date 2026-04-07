package com.springboot.simplyfly.mapper;

import com.springboot.simplyfly.dto.FlightDetailsDto;
import com.springboot.simplyfly.dto.RouteFlightSearchDto;
import com.springboot.simplyfly.model.RouteFlight;

public class RouteFlightMapper {

    public static RouteFlightSearchDto mapToDto(RouteFlight routeFlight){
        return new RouteFlightSearchDto(
               routeFlight.getId(),
               routeFlight.getRoute().getSourceAirport().getName(),
               routeFlight.getRoute().getDestinationAirport().getName(),
               routeFlight.getFlight().getFlightNumber(),
               routeFlight.getFlight().getOwner().getAirline_name(),
               routeFlight.getArrivalTime(),
               routeFlight.getDuration(),
               routeFlight.getDepartureTime(),
               routeFlight.getFare()
        );
    }
}