package com.springboot.simplyfly.controller;

import com.springboot.simplyfly.dto.FlightResDto;
import com.springboot.simplyfly.dto.FlightResPageDto;
import com.springboot.simplyfly.service.FlightService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/flight")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173/")
public class FlightController {
    private final FlightService flightService;

    @GetMapping("/get-by-owner")
    public FlightResPageDto getFlightsByOwner(Principal principal, @RequestParam (value = "page", required = false, defaultValue = "0") int page,
                                                    @RequestParam (value = "size", required = false, defaultValue = "0") int size){
        String username = principal.getName();
        return flightService.getFlightsByOwner(username, page, size);
    }

    @GetMapping("/get-details/{routeFlightId}")
    public FlightResDto getFlightDetailsById(Principal principal, @PathVariable long routeFlightId){
        String username = principal.getName();
        return flightService.getFlightDetailsById(username,routeFlightId);
    }
}