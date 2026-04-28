package com.springboot.simplyfly.service;

import com.springboot.simplyfly.dto.FlightResDto;
import com.springboot.simplyfly.dto.FlightResPageDto;
import com.springboot.simplyfly.exception.ResourceNotFoundException;
import com.springboot.simplyfly.exception.UnauthorizedAccessException;
import com.springboot.simplyfly.mapper.FlightMapper;
import com.springboot.simplyfly.model.Flight;
import com.springboot.simplyfly.model.Owner;
import com.springboot.simplyfly.model.Route;
import com.springboot.simplyfly.model.RouteFlight;
import com.springboot.simplyfly.repository.FlightRepository;
import com.springboot.simplyfly.repository.OwnerRepository;
import com.springboot.simplyfly.repository.RouteFlightRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FlightService {
    private final RouteFlightRepository routeFlightRepository;
    private final OwnerRepository ownerRepository;

    public FlightResPageDto getFlightsByOwner(String username, int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        Page<RouteFlight> pageList = routeFlightRepository.getFlightsByOwner(username, pageable);
        long totalRecords = pageList.getTotalElements();
        long totalPages = pageList.getTotalPages();
        List<RouteFlight> rfList = pageList.toList();
        List<FlightResDto> list = rfList
                .stream()
                .map(FlightMapper::mapToDto)
                .toList();

        return new FlightResPageDto(
                list,
                totalRecords,
                totalPages
        );
    }

    public FlightResDto getFlightDetailsById(String username, long routeFlightId) {
        Owner owner = ownerRepository.findByUsername(username);
        RouteFlight rf = routeFlightRepository.findById(routeFlightId)
                .orElseThrow(()->new ResourceNotFoundException("Invalid id"));

        if(!rf.getFlight().getOwner().getAppUser().getUsername().equals(username)){
            throw new UnauthorizedAccessException("This Flight doesn't belong to you");
        }

        return FlightMapper.mapToDto(rf);
    }
}