package com.springboot.simplyfly.mapper;

import com.springboot.simplyfly.dto.FlightResDto;
import com.springboot.simplyfly.model.RouteFlight;

public class FlightMapper {

    public static FlightResDto mapToDto(RouteFlight routeFlight){
        return new FlightResDto(
                routeFlight.getId(),
                routeFlight.getFlight().getFlightNumber(),
                routeFlight.getRoute().getSourceAirport().getCity(),
                routeFlight.getRoute().getDestinationAirport().getCity(),
                routeFlight.getRoute().getSourceAirport().getName(),
                routeFlight.getRoute().getDestinationAirport().getName(),
                routeFlight.getRoute().getSourceAirport().getCode(),
                routeFlight.getRoute().getDestinationAirport().getCode(),
                routeFlight.getArrivalTime(),
                routeFlight.getDepartureTime(),
                routeFlight.getTotalRows()*6,
                routeFlight.getAvailableSeats(),
                routeFlight.getFirstClassRows()*6,
                routeFlight.getBusinessClassRows()*6,
                routeFlight.getPremiumEconomyRows()*6,
                routeFlight.getEconomyRows()*6,
                routeFlight.getFare(),
                routeFlight.getPremiumEconomyFare(),
                routeFlight.getBusinessClassFare(),
                routeFlight.getFirstClassFare(),
                routeFlight.getFlight().getBaggageAllowed(),
                routeFlight.getFlight().getHandCarryAllowed()
        );
    }
}