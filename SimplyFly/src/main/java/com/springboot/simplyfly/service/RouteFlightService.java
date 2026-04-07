package com.springboot.simplyfly.service;

import com.springboot.simplyfly.dto.FlightDetailsDto;
import com.springboot.simplyfly.dto.RouteFlightSearchDto;
import com.springboot.simplyfly.dto.RouteFlightSearchReqDto;
import com.springboot.simplyfly.exception.ResourceNotFoundException;
import com.springboot.simplyfly.mapper.RouteFlightMapper;
import com.springboot.simplyfly.model.RouteFlight;
import com.springboot.simplyfly.repository.RouteFlightRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class RouteFlightService {
    private final RouteFlightRepository routeFlightRepository;
    private final ListableBeanFactory listableBeanFactory;

    public List<RouteFlightSearchDto> getFlightsBySourceAndDestination(RouteFlightSearchReqDto routeFlightSearchReqDto) {
        String source = routeFlightSearchReqDto.source();
        String destination = routeFlightSearchReqDto.destination();
        LocalDate date = routeFlightSearchReqDto.date();

        List<RouteFlight> list = routeFlightRepository.getFlightsBySourceAndDestination(source, destination, date);

        return list
                .stream()
                .map(RouteFlightMapper::mapToDto)
                .toList();
    }

    public FlightDetailsDto getFlightDetails(long routeFlightId) {
        RouteFlight routeFlight = routeFlightRepository.findById(routeFlightId)
                .orElseThrow(()->new ResourceNotFoundException("Flight details with this id not found."));

        return new FlightDetailsDto(
                routeFlight.getId(),
                routeFlight.getFlight().getOwner().getAirline_name(),
                routeFlight.getFlight().getFlightNumber(),
                routeFlight.getRoute().getSourceAirport().getCity(),
                routeFlight.getRoute().getDestinationAirport().getCity(),
                routeFlight.getRoute().getSourceAirport().getName(),
                routeFlight.getRoute().getDestinationAirport().getName(),
                routeFlight.getArrivalTime(),
                routeFlight.getDepartureTime(),
                routeFlight.getFlight().getTotalSeats(),
                routeFlight.getAvailableSeats(),
                routeFlight.getFlight().getBaggageAllowed()
        );
    }
}