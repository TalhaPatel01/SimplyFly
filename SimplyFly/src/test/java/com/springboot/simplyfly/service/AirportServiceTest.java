package com.springboot.simplyfly.service;

import com.springboot.simplyfly.exception.ResourceNotFoundException;
import com.springboot.simplyfly.model.Airport;
import com.springboot.simplyfly.repository.AirportRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AirportServiceTest {
    @InjectMocks
    private AirportService airportService;
    @Mock
    private AirportRepository airportRepository;

    @Test
    public void getAllAirportsTest(){
        Airport airport = new Airport();
        airport.setAirport_id(12L);
        airport.setName("Airport Name test");
        airport.setCode("BOM");
        airport.setCity("Mumbai");
        airport.setCountry("India");
        List<Airport> list = List.of(airport);

        Page<Airport> pageAirport = new PageImpl<>(list);
        int page = 0;
        int size=1;
        Pageable pageable = PageRequest.of(page,size);

        when(airportRepository.findAll(pageable)).thenReturn(pageAirport);
        Assertions.assertEquals(1,airportService.getAllAirports(0,1).list().size());
    }

    @Test
    public void getAirportByIdWhenExist(){
        Airport airport = new Airport();
        airport.setAirport_id(12L);
        airport.setName("Airport Name test");
        airport.setCode("BOM");
        airport.setCity("Mumbai");
        airport.setCountry("India");

        when(airportRepository.findById(12L)).thenReturn(Optional.of(airport));
        Assertions.assertEquals(airport,airportService.getAirportById(12L));
    }

    @Test
    public void getAirportByIdWhenNotExist(){
        when(airportRepository.findById(12L)).thenReturn(Optional.empty());

        Exception e = Assertions.assertThrows(ResourceNotFoundException.class,()->{
            airportService.getAirportById(12L);
        });

        Assertions.assertEquals("Invalid airport id",e.getMessage());
    }
}