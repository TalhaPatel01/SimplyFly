package com.springboot.simplyfly.controller;

//num of bookings for a given flight on a given date

import com.springboot.simplyfly.dto.CountBookingReqDto;
import com.springboot.simplyfly.service.TestService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test")
@AllArgsConstructor
public class TestController {
    private final TestService testService;

    @PostMapping("/count-bookings")
    public long countBookingsForFlightOnDate(@RequestBody CountBookingReqDto countBookingReqDto){
        return testService.countBookingsForFlightOnDate(countBookingReqDto);
    }
}