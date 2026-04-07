package com.springboot.simplyfly.controller;

import com.springboot.simplyfly.dto.BookingReqDto;
import com.springboot.simplyfly.dto.BookingResDto;
import com.springboot.simplyfly.service.BookingService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/booking")
@AllArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    //3. create booking with seat selection and list of passengers
    @PostMapping("/create-booking")
    public BookingResDto createBooking(@RequestBody BookingReqDto bookingReqDto,
                                       Principal principal){
        String username = principal.getName();
        return bookingService.createBooking(bookingReqDto,username);
    }

    //5. view booking
    @GetMapping("/get-by-user")
    public List<BookingResDto> getBookingsByUserId(Principal principal){
        String username = principal.getName();
        return bookingService.getBookingsByUserId(username);
    }

    //6. cancel booking
    @PutMapping("/cancel-booking/{bookingId}")
    public ResponseEntity<?> cancelBooking(@PathVariable long bookingId, Principal principal){
        String username = principal.getName();
        bookingService.cancelBooking(bookingId,username);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}