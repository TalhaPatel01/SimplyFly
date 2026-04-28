package com.springboot.simplyfly.controller;

import com.springboot.simplyfly.dto.PaymentDto;
import com.springboot.simplyfly.dto.TicketDto;
import com.springboot.simplyfly.service.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/payment")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173/")
public class PaymentController {
    private final PaymentService paymentService;

    //{Passenger} 4. Make Payment
    @PostMapping("/make-payment")
    public TicketDto makePayment(@RequestBody PaymentDto paymentDto, Principal principal){
        String username = principal.getName();
        return paymentService.makePayment(paymentDto, username);
    }

    //{Flight Owner} 1. Process Refund
    @PutMapping("/refund-payment/{bookingId}")
    public ResponseEntity<?> refundPaymentByBooking(@PathVariable long bookingId, Principal principal){
        String username = principal.getName();
        paymentService.refundPaymentByBooking(bookingId, username);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}