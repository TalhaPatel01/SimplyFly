package com.springboot.simplyfly.service;

import com.springboot.simplyfly.dto.*;
import com.springboot.simplyfly.enums.SeatClass;
import com.springboot.simplyfly.enums.SeatStatus;
import com.springboot.simplyfly.enums.SeatType;
import com.springboot.simplyfly.exception.ResourceNotFoundException;
import com.springboot.simplyfly.exception.UnauthorizedAccessException;
import com.springboot.simplyfly.mapper.RouteFlightMapper;
import com.springboot.simplyfly.model.*;
import com.springboot.simplyfly.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static com.springboot.simplyfly.mapper.RouteFlightMapper.mapToDtoWithFare;

@Service
@AllArgsConstructor
public class RouteFlightService {
    private final RouteFlightRepository routeFlightRepository;
    private final OwnerRepository ownerRepository;
    private final FlightRepository flightRepository;
    private final RouteRepository routeRepository;
    private final SeatRepository seatRepository;

    public List<RouteFlightSearchDto> getFlightsBySourceDestinationAndSeatClass(RouteFlightSearchReqDto routeFlightSearchReqDto) {
        String source = routeFlightSearchReqDto.source();
        String destination = routeFlightSearchReqDto.destination();
        LocalDate date = routeFlightSearchReqDto.date();
        SeatClass seatClass = routeFlightSearchReqDto.seatClass();

        List<RouteFlight> list = routeFlightRepository.getFlightsBySourceAndDestination(source, destination, date);

        return list
                .stream()
                .map(routeFlight -> mapToDtoWithFare(routeFlight,seatClass))
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
                routeFlight.getFlight().getBaggageAllowed(),
                routeFlight.getFlight().getHandCarryAllowed()
        );
    }

    private List<Seat> generateSeats(RouteFlight rf) {
        List<Seat> seats = new ArrayList<>();
        String columns = rf.getColumns();

        int firstEnd = rf.getFirstClassRows();
        int businessEnd = firstEnd + rf.getBusinessClassRows();
        int premiumEnd = businessEnd + rf.getPremiumEconomyRows();

        for (int row = 1; row <= rf.getTotalRows(); row++) {
            SeatClass seatClass;

            if (row <= firstEnd) {
                seatClass = SeatClass.FIRST_CLASS;
            } else if (row <= businessEnd) {
                seatClass = SeatClass.BUSINESS;
            } else if (row <= premiumEnd) {
                seatClass = SeatClass.PREMIUM_ECONOMY;
            } else {
                seatClass = SeatClass.ECONOMY;
            }

            for (int i = 0; i < columns.length(); i++) {
                char col = columns.charAt(i);

                Seat seat = new Seat();
                seat.setRouteFlight(rf);
                seat.setRowNumber(row);
                seat.setColumnName(String.valueOf(col));
                seat.setSeatNumber(row + String.valueOf(col));
                seat.setSeatStatus(SeatStatus.AVAILABLE);
                seat.setSeatClass(seatClass);

                if (i == 0 || i == columns.length() - 1) {
                    seat.setSeatType(SeatType.WINDOW);
                } else if (i == 2 || i == 3) {
                    seat.setSeatType(SeatType.AISLE);
                } else {
                    seat.setSeatType(SeatType.MIDDLE);
                }
                seats.add(seat);
            }
        }
        return seats;
    }

    public void addFlightInRoute(FlightAddReqDto dto, String username) {
        Owner owner = ownerRepository.findByUsername(username);

        int totalRows = dto.economyRows()+dto.premiumEconomyRows()+dto.businessClassRows()+dto.firstClassRows();
        Flight flight = new Flight();
        flight.setFlightNumber(dto.flightNumber());
        flight.setTotalSeats(totalRows * 6);
        flight.setBaggageAllowed(dto.baggageAllowed());
        flight.setHandCarryAllowed(dto.handCarryAllowed());
        flight.setOwner(owner);
        flightRepository.save(flight);

        Route route = routeRepository.findBySourceAndDestination(
                dto.sourceAirportCode(),
                dto.destinationAirportCode()
        ).orElseThrow(()->new ResourceNotFoundException("Route not found"));

        RouteFlight rf = new RouteFlight();
        rf.setFlight(flight);
        rf.setRoute(route);
        rf.setDate(dto.date());
        rf.setArrivalTime(dto.arrivalTime());
        rf.setDepartureTime(dto.departureTime());
        rf.setTotalRows(totalRows);
        rf.setColumns("ABCDEF");
        rf.setFirstClassRows(dto.firstClassRows());
        rf.setBusinessClassRows(dto.businessClassRows());
        rf.setPremiumEconomyRows(dto.premiumEconomyRows());
        rf.setEconomyRows(dto.economyRows());
        rf.setFare(dto.economyFare());
        rf.setPremiumEconomyFare(dto.premiumEconomyFare());
        rf.setBusinessClassFare(dto.businessClassFare());
        rf.setFirstClassFare(dto.firstClassFare());
        rf.setInfantFactor(dto.infantFactor());
        rf.setChildFactor(dto.childFactor());
        rf.setAvailableSeats(totalRows * 6);

        LocalTime dep = dto.departureTime();
        LocalTime arr = dto.arrivalTime();

        int duration = arr.isBefore(dep)
                ? (int) (Duration.between(dep, arr).toMinutes() + 1440)
                : (int) Duration.between(dep, arr).toMinutes();

        rf.setDuration(duration);
        routeFlightRepository.save(rf);

        List<Seat> seats = generateSeats(rf);
        seatRepository.saveAll(seats);
    }

    public void updateFlight(String username, long routeFlightId, FlightUpdateDto dto) {
        RouteFlight rf = routeFlightRepository.findById(routeFlightId)
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found"));

        if(!rf.getFlight().getOwner().getAppUser().getUsername().equals(username)){
            throw new UnauthorizedAccessException("This flight doesn't belong to you");
        }

        rf.setDepartureTime(dto.departureTime());
        rf.setArrivalTime(dto.arrivalTime());

        routeFlightRepository.save(rf);
    }

    public void updateFlightV2(String username, long routeFlightId, FlightUpdateDto flightUpdateDto) {
        RouteFlight rf = routeFlightRepository.findById(routeFlightId)
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found"));

        if(!rf.getFlight().getOwner().getAppUser().getUsername().equals(username)){
            throw new UnauthorizedAccessException("This flight doesn't belong to you");
        }

        LocalTime departureTime = flightUpdateDto.departureTime();
        LocalTime arrivalTime = flightUpdateDto.arrivalTime();

        routeFlightRepository.updateFlight(routeFlightId,departureTime,arrivalTime);
    }
}