package com.springboot.simplyfly.repository;

import com.springboot.simplyfly.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat,Long> {
    List<Seat> findByRouteFlightIdAndSeatNumberIn(long routeFlightId, List<String> seatNumbers);
}