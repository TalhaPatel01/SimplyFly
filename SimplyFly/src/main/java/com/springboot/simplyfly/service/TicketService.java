package com.springboot.simplyfly.service;

import com.springboot.simplyfly.dto.PassengerDto;
import com.springboot.simplyfly.dto.TicketDetailsDto;
import com.springboot.simplyfly.exception.ResourceNotFoundException;
import com.springboot.simplyfly.model.Booking;
import com.springboot.simplyfly.model.Passenger;
import com.springboot.simplyfly.model.User;
import com.springboot.simplyfly.repository.BookingRepository;
import com.springboot.simplyfly.repository.PassengerRepository;
import com.springboot.simplyfly.repository.TicketRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.event.Level;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class TicketService {
    private final BookingRepository bookingRepository;
    private final PassengerRepository passengerRepository;
    private final UserService userService;

    public TicketDetailsDto downloadTicket(long bookingId, String username) {
        log.atLevel(Level.INFO).log("Download Ticket Called...");

        User user = userService.findByUsername(username);

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(()->new ResourceNotFoundException("Booking not found"));

        List<Passenger> passengerList = passengerRepository.findPassengerByBookingId(bookingId);
        List<PassengerDto> list = passengerList
                .stream()
                .map(p-> new PassengerDto(
                        p.getName(),
                        p.getAge(),
                        p.getGender(),
                        p.getPassengerType(),
                        p.getSeat() != null ? p.getSeat().getSeatNumber() : "No Seat"
                ))
                .toList();

        log.atLevel(Level.INFO).log("Ticket Downloaded....");

        return new TicketDetailsDto(
               bookingId,
               booking.getFlight().getRoute().getSourceAirport().getCity(),
               booking.getFlight().getRoute().getSourceAirport().getName(),
               booking.getFlight().getRoute().getDestinationAirport().getCity(),
               booking.getFlight().getRoute().getDestinationAirport().getName(),
               booking.getDepartureTime(),
                booking.getArrivalTime(),
                booking.getSeatClass(),
                booking.getTotalAmount(),
                booking.getTravelDate(),
                booking.getBookingDate(),
                booking.getFlight().getFlight().getFlightNumber(),
                booking.getFlight().getFlight().getOwner().getAirline_name(),
                booking.getFlight().getFlight().getBaggageAllowed(),
                booking.getFlight().getFlight().getHandCarryAllowed(),
                list
        );
    }
}