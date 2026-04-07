package com.springboot.simplyfly.controller;

import com.springboot.simplyfly.dto.TicketDto;
import com.springboot.simplyfly.service.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/payment")
@AllArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    //4. Make Payment
    @PostMapping("/make-payment/{bookingId}")
    public TicketDto makePayment(@PathVariable long bookingId, Principal principal){
        String username = principal.getName();
        return paymentService.makePayment(bookingId, username);
    }
}