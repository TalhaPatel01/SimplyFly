package com.springboot.simplyfly.dto;

import com.springboot.simplyfly.enums.TicketStatus;

import java.time.Instant;

public record TicketDto(
        long booking_id,
        String passengerDetails,
        TicketStatus ticketStatus,
        int baggageAllowed,
        Instant createdAt
) {
}