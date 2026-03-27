package com.springboot.simplyfly.repository;

import com.springboot.simplyfly.model.Airport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirportRepository extends JpaRepository<Airport,Long> {
}
