package com.springboot.simplyfly.controller;

import com.springboot.simplyfly.dto.*;
import com.springboot.simplyfly.service.RouteFlightService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/route/flight")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173/")
public class RouteFlightController {
    private final RouteFlightService routeFlightService;

    //{Passenger} 1. searching flight based on source, destination and traveldate
    @PostMapping("/search")
    public List<RouteFlightSearchDto> getFlightsBySourceDestinationAndSeatClass(@RequestBody RouteFlightSearchReqDto routeFlightSearchReqDto) {
        return routeFlightService.getFlightsBySourceDestinationAndSeatClass(routeFlightSearchReqDto);
    }

    //{Passenger}  2. selecting flight and getting details
    @GetMapping("/flight-details/{routeFlightId}")
    public FlightDetailsDto getFlightDetails(@PathVariable long routeFlightId){
        return routeFlightService.getFlightDetails(routeFlightId);
    }

   //{Flight Owner} 2. add flight in route
    @PostMapping("/add-flight")
    public ResponseEntity<?> addFlightInRoute(@RequestBody FlightAddReqDto flightAddReqDto, Principal principal){
        String username = principal.getName();
        routeFlightService.addFlightInRoute(flightAddReqDto, username);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/update/{routeFlightId}")
    public ResponseEntity<?> updateFlight(Principal principal, @PathVariable long routeFlightId,
                                          @RequestBody FlightUpdateDto dto) {
        String username = principal.getName();
        routeFlightService.updateFlight(username,routeFlightId, dto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/update/{routeFlightId}/v2")
    public ResponseEntity<?> updateFlightV2(Principal principal, @PathVariable long routeFlightId,
                                            @RequestBody FlightUpdateDto flightUpdateDto) {
        String username = principal.getName();
        routeFlightService.updateFlightV2(username,routeFlightId,flightUpdateDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}