package com.springboot.simplyfly.model;

import com.springboot.simplyfly.enums.TicketStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;

    private String passengerDetails;

    @Enumerated(EnumType.STRING)
    private TicketStatus ticketStatus;

    private int baggageAllowed;

    @CreationTimestamp
    @Column(nullable = false,updatable = false)
    private Instant createdAt;

    @CreationTimestamp
    private Instant updatedAt;
}