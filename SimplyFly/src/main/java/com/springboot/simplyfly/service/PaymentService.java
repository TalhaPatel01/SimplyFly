package com.springboot.simplyfly.service;

import com.springboot.simplyfly.dto.PaymentDto;
import com.springboot.simplyfly.dto.TicketDto;
import com.springboot.simplyfly.enums.*;
import com.springboot.simplyfly.exception.BookingNotCancelledException;
import com.springboot.simplyfly.exception.ResourceNotFoundException;
import com.springboot.simplyfly.exception.UnauthorizedAccessException;
import com.springboot.simplyfly.model.*;
import com.springboot.simplyfly.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import static com.springboot.simplyfly.enums.BookingStatus.CANCELLED;

@Service
@AllArgsConstructor
public class PaymentService {
    private final BookingRepository bookingRepository;
    private final PassengerRepository passengerRepository;
    private final SeatRepository seatRepository;
    private final TicketRepository ticketRepository;
    private final PaymentRepository paymentRepository;

    public TicketDto makePayment(PaymentDto paymentDto, String username) {
        Booking booking = bookingRepository.findById(paymentDto.bookingId())
                .orElseThrow(()->new ResourceNotFoundException("Invalid Booking Id"));

        if(!booking.getUser().getAppUser().getUsername().equals(username)){
            throw new UnauthorizedAccessException("Unauthorized access");
        }

        //setting payment
        Payment payment = new Payment();
        payment.setBooking(booking);
        payment.setAmount(booking.getTotalAmount());
        payment.setPaymentMethod(paymentDto.paymentMethod());
        payment.setStatus(PaymentStatus.SUCCESS);
        payment.setPaymentTime(Instant.now());
        paymentRepository.save(payment);

        //updating status of booking
        booking.setBookingStatus(BookingStatus.CONFIRMED);
        bookingRepository.save(booking);

        //fetching passengers by that booking id
        List<Passenger> passengers = passengerRepository.findPassengerByBookingId(paymentDto.bookingId());

        //updating seats from PENDING TO BOOKED
        passengers.forEach(passenger -> {
            if (passenger.getSeat() != null) {
                Seat seat = passenger.getSeat();
                seat.setSeatStatus(SeatStatus.BOOKED);
                seatRepository.save(seat);
            }
        });

        String passengerDetails = passengers
                .stream()
                .map(p -> {
                    if (p.getSeat() != null) {
                        return p.getName() + " - " + p.getSeat().getSeatNumber();
                    } else {
                        return p.getName() + " - INFANT (No Seat)";
                    }
                })
                .collect(Collectors.joining(", "));

        //create ticket
        Ticket ticket = new Ticket();
        ticket.setBooking(booking);
        ticket.setPassengerDetails(passengerDetails);
        ticket.setTicketStatus(TicketStatus.CONFIRMED);
        ticket.setBaggageAllowed(booking.getFlight().getFlight().getBaggageAllowed());
        ticketRepository.save(ticket);

        return new TicketDto(
              paymentDto.bookingId(),
              passengerDetails,
              TicketStatus.CONFIRMED,
              booking.getFlight().getFlight().getBaggageAllowed(),
              ticket.getCreatedAt()
        );
    }

    public Payment findPaymentByBookingId(long bookingId) {
        return paymentRepository.findPaymentByBookingId(bookingId);
    }

    public void refundPaymentByBooking(long bookingId, String username) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(()->new ResourceNotFoundException("Invalid Booking id"));

        if(!booking.getFlight().getFlight().getOwner().getAppUser().getUsername().equals(username)){
            throw new UnauthorizedAccessException("No access to process refund for this payment");
        }

        if(booking.getBookingStatus() != CANCELLED){
            throw new BookingNotCancelledException("This Booking is not cancelled");
        }

        Payment payment = paymentRepository.findPaymentByBookingId(bookingId);

        payment.setStatus(PaymentStatus.REFUNDED);
        paymentRepository.save(payment);
    }
}