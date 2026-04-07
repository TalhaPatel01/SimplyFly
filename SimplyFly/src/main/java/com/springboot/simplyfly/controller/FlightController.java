package com.springboot.simplyfly.controller;

import com.springboot.simplyfly.dto.FlightResDto;
import com.springboot.simplyfly.service.FlightService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/flight")
@AllArgsConstructor
public class FlightController {
    private final FlightService flightService;

    @GetMapping("/owner/{ownerId}")
    public List<FlightResDto> getFlightsByOwner(@PathVariable int ownerId){
        return flightService.getFlightsByOwner(ownerId);
    }
}