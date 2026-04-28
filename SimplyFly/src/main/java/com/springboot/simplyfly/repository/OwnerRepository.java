package com.springboot.simplyfly.repository;

import com.springboot.simplyfly.model.Owner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OwnerRepository extends JpaRepository<Owner,Long> {

    @Query("""
            select o from Owner o
            where o.appUser.username = ?1
            """)
    Owner findByUsername(String username);

    @Query("""
            select COALESCE(count(f),0) from Flight f
            where f.owner.appUser.username = ?1
            """)
    long getTotalFlightsByOwner(String username);

    @Query("""
            select COALESCE(count(b),0) from Booking b
            where b.flight.flight.owner.appUser.username = ?1
            """)
    long getTotalBookingsByOwner(String username);

    @Query("""
            select COALESCE(SUM(b.totalAmount),0) from Booking b
            where b.bookingStatus = "CONFIRMED"
            and b.flight.flight.owner.appUser.username = ?1
            """)
    long getOwnerTotalRevenue(String username);

    @Query("""
            select o from Owner o
            where o.appUser.role = "OWNER"
            """)
    Page<Owner> findAllOwner(Pageable pageable);
}