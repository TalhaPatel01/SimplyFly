package com.springboot.simplyfly.repository;

import com.springboot.simplyfly.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PaymentRepository extends JpaRepository<Payment,Long> {

    @Query("""
            select p from Payment p
            where p.booking.id = ?1
            """)
    Payment findPaymentByBookingId(long bookingId);
}