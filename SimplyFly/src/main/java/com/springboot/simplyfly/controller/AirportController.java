package com.springboot.simplyfly.controller;

import com.springboot.simplyfly.dto.AirportPageDto;
import com.springboot.simplyfly.dto.AirportReqDto;
import com.springboot.simplyfly.dto.AirportResDto;
import com.springboot.simplyfly.model.Airport;
import com.springboot.simplyfly.service.AirportService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/airport")
@CrossOrigin(origins = "http://localhost:5173/")
public class AirportController {
    private final AirportService airportService;

    @PostMapping("/add")
    public ResponseEntity<?> addAirport(@RequestBody AirportReqDto airportReqDto){
        airportService.addAirport(airportReqDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/pagination/get-all")
    public AirportPageDto getAllAirportWithPagination(@RequestParam (value = "page", required = false, defaultValue = "0") int page,
                                        @RequestParam (value = "size", required = false, defaultValue = "0") int size){
        return airportService.getAllAirports(page,size);
    }

    @GetMapping("get/{id}")
    public Airport getAirportById(@PathVariable long id){
        return airportService.getAirportById(id);
    }

    @GetMapping("/get-all")
    public List<AirportResDto> getAirports(){
        return airportService.getAirports();
    }
}