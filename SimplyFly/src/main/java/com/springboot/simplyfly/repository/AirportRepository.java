package com.springboot.simplyfly.repository;

import com.springboot.simplyfly.model.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AirportRepository extends JpaRepository<Airport,Long> {

    @Query("""
            select a from Airport a
            where a.code = ?1
            """)
    Airport findByCode(String s);
}
