package com.springboot.simplyfly.repository;

import com.springboot.simplyfly.model.RouteFlight;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface RouteFlightRepository extends JpaRepository<RouteFlight,Long> {

    @Query("""
            select rf from RouteFlight rf
            where rf.route.sourceAirport.city = ?1 and rf.route.destinationAirport.city = ?2
            and rf.date = ?3
            """)
    List<RouteFlight> getFlightsBySourceAndDestination(String source, String destination, LocalDate date);

    @Modifying
    @Transactional
    @Query("""
            update RouteFlight rf
            set rf.availableSeats = rf.availableSeats - ?2
            where rf.id = ?1 and rf.availableSeats >= ?2
            """)
    int updateSeats(long l, int seats);

    @Query("""
            select rf from RouteFlight rf
            where rf.flight.owner.appUser.username = ?1
            """)
    Page<RouteFlight> getFlightsByOwner(String username, Pageable pageable);

    @Query("""
            update RouteFlight rf
            set rf.departureTime = ?2, rf.arrivalTime = ?3
            where rf.id = ?1
            """)
    void updateFlight(long routeFlightId, LocalTime departureTime, LocalTime arrivalTime);
}