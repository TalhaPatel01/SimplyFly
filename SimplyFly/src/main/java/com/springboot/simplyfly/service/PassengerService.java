package com.springboot.simplyfly.service;

import com.springboot.simplyfly.dto.PassengerDetailsDto;
import com.springboot.simplyfly.dto.PassengerDetailsPageDto;
import com.springboot.simplyfly.exception.ResourceNotFoundException;
import com.springboot.simplyfly.mapper.PassengerMapper;
import com.springboot.simplyfly.model.Passenger;
import com.springboot.simplyfly.model.RouteFlight;
import com.springboot.simplyfly.repository.PassengerRepository;
import com.springboot.simplyfly.repository.RouteFlightRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class PassengerService {
    private final PassengerRepository passengerRepository;

    public PassengerDetailsPageDto getPassengersByRouteFlight(String username, long routeFlightId, int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        Page<Passenger> pagePassenger = passengerRepository.findPassengerByRouteFlight(routeFlightId,pageable);
        long totalRecords = pagePassenger.getTotalElements();
        long totalPages = pagePassenger.getTotalPages();
        List<Passenger> passengerList = pagePassenger.toList();
        List<PassengerDetailsDto> list = passengerList
                .stream()
                .map(PassengerMapper::mapToDto)
                .toList();

        return new PassengerDetailsPageDto(
              list,
              totalRecords,
              totalPages
        );
    }
}