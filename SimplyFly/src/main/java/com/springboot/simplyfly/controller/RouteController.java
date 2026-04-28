package com.springboot.simplyfly.controller;

import com.springboot.simplyfly.dto.RouteAddReqDto;
import com.springboot.simplyfly.dto.RoutePageResDto;
import com.springboot.simplyfly.service.RouteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/route")
@CrossOrigin(origins = "http://localhost:5173/")
public class RouteController {
    private final RouteService routeService;

    @PostMapping("/add-route")
    public ResponseEntity<?> addRoute(@RequestBody RouteAddReqDto routeAddReqDto){
        routeService.addRoute(routeAddReqDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/get-all")
    public RoutePageResDto getAllRoutes(@RequestParam (value = "page", required = false, defaultValue = "0") int page,
                                        @RequestParam (value = "size", required = false, defaultValue = "0") int size){
        return routeService.getAllRoutes(page,size);
    }
}