package com.springboot.simplyfly.controller;

import com.springboot.simplyfly.dto.TicketDetailsDto;
import com.springboot.simplyfly.service.TicketService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/ticket")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173/")
public class TicketController {
    private final TicketService ticketService;

    @GetMapping("/download/{bookingId}")
    public TicketDetailsDto downloadTicket(@PathVariable long bookingId, Principal principal){
        String username = principal.getName();
        return ticketService.downloadTicket(bookingId,username);
    }
}