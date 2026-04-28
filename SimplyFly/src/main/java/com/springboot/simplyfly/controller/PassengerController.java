package com.springboot.simplyfly.controller;

import com.springboot.simplyfly.dto.PassengerDetailsPageDto;
import com.springboot.simplyfly.repository.PassengerRepository;
import com.springboot.simplyfly.service.PassengerService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("api/passenger")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173/")
public class PassengerController {
    private final PassengerService passengerService;

    @GetMapping("/{routeFlightId}")
    public PassengerDetailsPageDto getPassengersByRouteFlight(Principal principal, @PathVariable long routeFlightId,
                                                              @RequestParam (value = "page", required = false, defaultValue = "0") int page,
                                                              @RequestParam (value = "size", required = false, defaultValue = "0") int size){
        String username = principal.getName();
        return passengerService.getPassengersByRouteFlight(username,routeFlightId,page,size);
    }
}