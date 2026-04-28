package com.springboot.simplyfly.repository;

import com.springboot.simplyfly.model.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RouteRepository extends JpaRepository<Route,Long> {

    @Query("""
           select r from Route r
           where r.sourceAirport.code = ?1
           and r.destinationAirport.code = ?2
           """)
    Optional<Route> findBySourceAndDestination(String s, String s1);

    @Query("""
            select count(r.route_id)
            from Route r
            """)
    long findTotalRoutes();
}