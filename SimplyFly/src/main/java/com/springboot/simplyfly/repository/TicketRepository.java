package com.springboot.simplyfly.repository;

import com.springboot.simplyfly.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket,Long> {
}