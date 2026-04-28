package com.springboot.simplyfly.controller;

import com.springboot.simplyfly.dto.SeatMapResDto;
import com.springboot.simplyfly.dto.SeatResDto;
import com.springboot.simplyfly.enums.SeatClass;
import com.springboot.simplyfly.service.SeatService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/seat")
@CrossOrigin(origins = "http://localhost:5173/")
public class SeatController {
    private final SeatService seatService;

    @GetMapping("/get-all")
    public List<SeatResDto> getSeatsByRouteFlightAndSeatClass(@RequestParam long routeFlightId, @RequestParam SeatClass seatClass){
        return seatService.getSeatsByRouteFlight(routeFlightId, seatClass);
    }

    @GetMapping("/get/seat-map/{routeFlightId}")
    public SeatMapResDto getSeatMap(Principal principal, @PathVariable long routeFlightId){
        String username = principal.getName();
        return seatService.getSeatMap(username,routeFlightId);
    }
}