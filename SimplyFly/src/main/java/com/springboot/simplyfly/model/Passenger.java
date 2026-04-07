package com.springboot.simplyfly.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Passenger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private int age;
    private String gender;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;

    @OneToOne
    @JoinColumn(name = "seat_id")
    private Seat seat;
}