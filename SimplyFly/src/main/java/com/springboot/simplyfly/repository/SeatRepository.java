package com.springboot.simplyfly.repository;

import com.springboot.simplyfly.enums.SeatClass;
import com.springboot.simplyfly.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat,Long> {
    List<Seat> findByRouteFlightIdAndSeatNumberIn(long routeFlightId, List<String> seatNumbers);

    @Query("""
            select s from Seat s
            where s.routeFlight.id = ?1
            and s.seatClass = ?2
            """)
    List<Seat> findByRouteFlightIdAndSeatClass(long routeFlightId, SeatClass seatClass);

    @Query("""
           select s from Seat s
           where s.routeFlight.id = ?1
           """)
    List<Seat> findByRouteFlight(long routeFlightId);
}