package com.springboot.simplyfly.service;

import com.springboot.simplyfly.dto.SeatMapResDto;
import com.springboot.simplyfly.dto.SeatResDto;
import com.springboot.simplyfly.enums.SeatClass;
import com.springboot.simplyfly.exception.ResourceNotFoundException;
import com.springboot.simplyfly.exception.UnauthorizedAccessException;
import com.springboot.simplyfly.mapper.SeatMapper;
import com.springboot.simplyfly.model.RouteFlight;
import com.springboot.simplyfly.model.Seat;
import com.springboot.simplyfly.repository.RouteFlightRepository;
import com.springboot.simplyfly.repository.SeatRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SeatService {
    private final SeatRepository seatRepository;
    private final RouteFlightRepository routeFlightRepository;

    public List<SeatResDto> getSeatsByRouteFlight(long routeFlightId, SeatClass seatClass) {
        List<Seat> list = seatRepository.findByRouteFlightIdAndSeatClass(routeFlightId, seatClass);

        return list
                .stream()
                .map(SeatMapper::mapToDto)
                .toList();
    }

    public SeatMapResDto getSeatMap(String username, long routeFlightId) {
        RouteFlight routeFlight = routeFlightRepository.findById(routeFlightId)
                .orElseThrow(()->new ResourceNotFoundException("Invalid id"));

        if(!routeFlight.getFlight().getOwner().getAppUser().getUsername().equals(username)){
            throw new UnauthorizedAccessException("This Flight doesn't belong to you");
        }

        long totalSeats = routeFlight.getTotalRows()* 6L;
        long availableSeats = routeFlight.getAvailableSeats();
        long bookedSeats = totalSeats-availableSeats;

        List<Seat> seats = seatRepository.findByRouteFlight(routeFlightId);
        List<SeatResDto> list = seats
                .stream()
                .map(SeatMapper::mapToDto)
                .toList();

        return new SeatMapResDto(
               list,
               totalSeats,
               bookedSeats,
               availableSeats
        );
    }
}