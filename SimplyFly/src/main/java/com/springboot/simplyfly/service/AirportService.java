package com.springboot.simplyfly.service;

import com.springboot.simplyfly.dto.AirportPageDto;
import com.springboot.simplyfly.dto.AirportReqDto;
import com.springboot.simplyfly.dto.AirportResDto;
import com.springboot.simplyfly.exception.ResourceNotFoundException;
import com.springboot.simplyfly.mapper.AirportMapper;
import com.springboot.simplyfly.model.Airport;
import com.springboot.simplyfly.repository.AirportRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AirportService {
    private final AirportRepository airportRepository;

    public void addAirport(AirportReqDto airportReqDto) {
        Airport airport = AirportMapper.mapToEntity(airportReqDto);
        airportRepository.save(airport);
    }

    public AirportPageDto getAllAirports(int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        Page<Airport> airport = airportRepository.findAll(pageable);
        long totalRecords = airport.getTotalElements();
        int totalPages = airport.getTotalPages();

        List<Airport> list = airport.toList();

        return new AirportPageDto(
                list,
                totalRecords,
                totalPages
        );
    }

    public Airport getAirportById(long id) {
        return airportRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Invalid airport id"));
    }

    public List<AirportResDto> getAirports() {
        List<Airport> list = airportRepository.findAll();
        return list
                .stream()
                .map(AirportMapper::mapToDto)
                .toList();
    }
}