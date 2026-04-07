package com.springboot.simplyfly.repository;

import com.springboot.simplyfly.model.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PassengerRepository extends JpaRepository<Passenger,Long> {

    @Query("""
            select p from Passenger p
            where p.booking.id = ?1
            """)
    List<Passenger> findPassengerByBookingId(long bookingId);
}