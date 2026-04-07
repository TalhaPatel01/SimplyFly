package com.springboot.simplyfly.controller;

import com.springboot.simplyfly.dto.FlightDetailsDto;
import com.springboot.simplyfly.dto.RouteFlightSearchDto;
import com.springboot.simplyfly.dto.RouteFlightSearchReqDto;
import com.springboot.simplyfly.service.RouteFlightService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/route/flight")
@AllArgsConstructor
public class RouteFlightController {
    private final RouteFlightService routeFlightService;

    // 1. searching flight based on source, destination and traveldate
    @PostMapping("/search")
    public List<RouteFlightSearchDto> getFlightsBySourceAndDestination(@RequestBody RouteFlightSearchReqDto routeFlightSearchReqDto) {
        return routeFlightService.getFlightsBySourceAndDestination(routeFlightSearchReqDto);
    }

    //2. selecting flight and getting details
    @GetMapping("/flight-details/{routeFlightId}")
    public FlightDetailsDto getFlightDetails(@PathVariable long routeFlightId){
        return routeFlightService.getFlightDetails(routeFlightId);
    }
}