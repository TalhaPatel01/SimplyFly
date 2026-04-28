package com.springboot.simplyfly.repository;

import com.springboot.simplyfly.dto.TopRouteDto;
import com.springboot.simplyfly.model.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
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

    @Query("""
            select b from Booking b
            where b.flight.flight.owner.appUser.username = ?1
            """)
    Page<Booking> findByOwner(String username, Pageable pageable);

    @Query("""
            select new com.springboot.simplyfly.dto.TopRouteDto(
                        b.flight.route.sourceAirport.city,
                        b.flight.route.destinationAirport.city,
                        count(b.id)
                        )
            from Booking b
            where b.flight.flight.owner.appUser.username = ?1
            group by b.flight.route.sourceAirport.city, b.flight.route.destinationAirport.city
            order by count(b.id) DESC
            """)
    List<TopRouteDto> getTopRoutes(String username, Pageable pageable);

    @Query("""
            select count(b.id)
            from Booking b
            where b.bookingStatus = "CONFIRMED"
            """)
    long findTotalBookings();

    @Query("""
            select count(b.id)
            from Booking b
            where b.flight.flight.flightNumber = ?1
            and b.bookingDate = ?2
            """)
    long countBooking(String flightNumber, LocalDate date);
}