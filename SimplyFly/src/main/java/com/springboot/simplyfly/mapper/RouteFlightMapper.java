package com.springboot.simplyfly.mapper;

import com.springboot.simplyfly.dto.FlightDetailsDto;
import com.springboot.simplyfly.dto.RouteFlightSearchDto;
import com.springboot.simplyfly.enums.SeatClass;
import com.springboot.simplyfly.model.RouteFlight;

public class RouteFlightMapper {

    public static RouteFlightSearchDto mapToDtoWithFare(RouteFlight routeFlight, SeatClass seatClass){
        double fare = switch(seatClass){
            case ECONOMY -> routeFlight.getFare();
            case PREMIUM_ECONOMY -> routeFlight.getPremiumEconomyFare();
            case BUSINESS -> routeFlight.getBusinessClassFare();
            case FIRST_CLASS -> routeFlight.getFirstClassFare();
        };

        return new RouteFlightSearchDto(
               routeFlight.getId(),
               routeFlight.getRoute().getSourceAirport().getName(),
               routeFlight.getRoute().getDestinationAirport().getName(),
               routeFlight.getFlight().getFlightNumber(),
               routeFlight.getFlight().getOwner().getAirline_name(),
               routeFlight.getArrivalTime(),
               routeFlight.getDuration(),
               routeFlight.getDepartureTime(),
               fare
        );
    }
}