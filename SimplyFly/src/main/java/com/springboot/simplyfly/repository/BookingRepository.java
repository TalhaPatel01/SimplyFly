package com.springboot.simplyfly.repository;

import com.springboot.simplyfly.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking,Long> {

    @Query("""
            select b from Booking b
            where b.user.user_id = ?1
            """)
    List<Booking> findByUserId(long userId);

    @Query("""
            select b from Booking b
            where b.id = ?1
            and b.user.appUser.username = ?2
            """)
    Booking findBookingByUserAndBookingId(long bookingId, String username);
}