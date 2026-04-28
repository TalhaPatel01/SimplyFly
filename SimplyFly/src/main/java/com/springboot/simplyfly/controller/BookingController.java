package com.springboot.simplyfly.controller;

import com.springboot.simplyfly.dto.*;
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
@CrossOrigin(origins = "http://localhost:5173/")
public class BookingController {
    private final BookingService bookingService;

    //{Passenger} 3. create booking with seat selection and list of passengers
    @PostMapping("/create-booking")
    public BookingResDto createBooking(@RequestBody BookingReqDto bookingReqDto,
                                       Principal principal){
        String username = principal.getName();
        return bookingService.createBooking(bookingReqDto,username);
    }

    //{Passenger} 5. view booking
    @GetMapping("/get-by-user")
    public List<UserBookingResDto> getBookingsByUserId(Principal principal){
        String username = principal.getName();
        return bookingService.getBookingsByUserId(username);
    }

    //{Passenger} 6. cancel booking
    @PutMapping("/cancel-booking/{bookingId}")
    public ResponseEntity<?> cancelBooking(@PathVariable long bookingId, Principal principal){
        String username = principal.getName();
        bookingService.cancelBooking(bookingId,username);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{bookingId}")
    public BookingFullResDto getBookingById(@PathVariable long bookingId, Principal principal
    ) {
        String username = principal.getName();
        return bookingService.getBookingById(bookingId, username);
    }

    @GetMapping("/get-by-owner")
    public BookingPageFullResDto getBookingsByOwner(Principal principal,
                                                    @RequestParam (value = "page", required = false, defaultValue = "0") int page,
                                                    @RequestParam (value = "size", required = false, defaultValue = "0") int size){
        String username = principal.getName();
        return bookingService.getBookingsByOwner(username,page,size);
    }
}