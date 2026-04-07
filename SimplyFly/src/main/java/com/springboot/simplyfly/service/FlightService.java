package com.springboot.simplyfly.service;

import com.springboot.simplyfly.dto.FlightResDto;
import com.springboot.simplyfly.mapper.FlightMapper;
import com.springboot.simplyfly.model.Flight;
import com.springboot.simplyfly.repository.FlightRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FlightService {
    private final FlightRepository flightRepository;

    public List<FlightResDto> getFlightsByOwner(int ownerId) {
        List<Flight> list = flightRepository.getFlightByOwner(ownerId);

        return list.stream()
                .map(FlightMapper::mapToDto)
                .toList();
    }
}