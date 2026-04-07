package com.springboot.simplyfly.repository;

import com.springboot.simplyfly.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FlightRepository extends JpaRepository<Flight,Long> {

    @Query("""
            select f from Flight f
            where f.owner.owner_id = ?1
            """)
    List<Flight> getFlightByOwner(int ownerId);
}