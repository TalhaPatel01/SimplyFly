package com.springboot.simplyfly.repository;

import com.springboot.simplyfly.model.Passenger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PassengerRepository extends JpaRepository<Passenger,Long> {

    @Query("""
            select p from Passenger p
            where p.booking.id = ?1
            """)
    List<Passenger> findPassengerByBookingId(long bookingId);

    @Query("""
            select count(p.id) from Passenger p
            where p.booking.id = ?1
            """)
    int countByBookingId(long id);

    @Query("""
            select p from Passenger p
            where p.booking.flight.id = ?1
            """)
    Page<Passenger> findPassengerByRouteFlight(long routeFlightId, Pageable pageable);
}