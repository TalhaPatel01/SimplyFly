package com.springboot.simplyfly.service;

import com.springboot.simplyfly.dto.CountBookingReqDto;
import com.springboot.simplyfly.repository.BookingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class TestService {
    private final BookingRepository bookingRepository;

    public long countBookingsForFlightOnDate(CountBookingReqDto countBookingReqDto) {
        String flightNumber = countBookingReqDto.flightNumber();
        LocalDate date = countBookingReqDto.date();

        return bookingRepository.countBooking(flightNumber, date);
    }
}