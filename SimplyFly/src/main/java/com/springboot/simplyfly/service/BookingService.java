package com.springboot.simplyfly.service;

import com.springboot.simplyfly.dto.BookingReqDto;
import com.springboot.simplyfly.dto.BookingResDto;
import com.springboot.simplyfly.dto.PassengerDto;
import com.springboot.simplyfly.enums.BookingStatus;
import com.springboot.simplyfly.enums.PaymentStatus;
import com.springboot.simplyfly.enums.SeatStatus;
import com.springboot.simplyfly.exception.InvalidSeatSelected;
import com.springboot.simplyfly.exception.ResourceNotFoundException;
import com.springboot.simplyfly.exception.SeatsNotAvailableException;
import com.springboot.simplyfly.exception.UnauthorizedAccessException;
import com.springboot.simplyfly.mapper.BookingMapper;
import com.springboot.simplyfly.model.*;
import com.springboot.simplyfly.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookingService {
    private final RouteFlightRepository routeFlightRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final UserService userService;
    private final PassengerRepository passengerRepository;
    private final SeatRepository seatRepository;
    private final PaymentService paymentService;
    private final PaymentRepository paymentRepository;

    public BookingResDto createBooking(BookingReqDto bookingReqDto, String username) {
        User user = userService.findByUsername(username);

        RouteFlight routeFlight = routeFlightRepository.findById(bookingReqDto.routeFlightId())
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found"));

        // taking seat numbers from passenger list in dto
        List<String> seatNumbers = bookingReqDto.passengerList()
                .stream()
                .map(PassengerDto::seatNumber)
                .toList();

        //fetching seats from Seat which are equal in DTO
        List<Seat> seats = seatRepository
                .findByRouteFlightIdAndSeatNumberIn(
                        bookingReqDto.routeFlightId(),
                        seatNumbers
                );

        //validating if seats exist
        if (seats.size() != seatNumbers.size()) {
            throw new InvalidSeatSelected("Invalid seat selected");
        }

        //checking seat availability
        for (Seat seat : seats) {
            if (seat.getSeatStatus() != SeatStatus.AVAILABLE) {
                throw new SeatsNotAvailableException("Selected seat is already booked");
            }
        }

        //updating available seats
        int updatedRows = routeFlightRepository.updateSeats(
                bookingReqDto.routeFlightId(),
                seatNumbers.size()
        );

        if (updatedRows == 0) {
            throw new SeatsNotAvailableException("Not enough seats available");
        }

        //marking seats as PENDING (after payment complete mark as booked)
        seats.forEach(seat -> seat.setSeatStatus(SeatStatus.PENDING));
        seatRepository.saveAll(seats);

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setFlight(routeFlight);
        booking.setBookingDate(LocalDate.now());
        booking.setTravelDate(bookingReqDto.travelDate());
        booking.setArrivalTime(routeFlight.getArrivalTime());
        booking.setDepartureTime(routeFlight.getDepartureTime());
        booking.setSeatsBooked(seatNumbers.size());

        double amount = routeFlight.getFare() * seatNumbers.size();
        booking.setTotalAmount(amount);
        booking.setBookingStatus(BookingStatus.PENDING);

        bookingRepository.save(booking);

        // mapping seats for passenger assignment
        Map<String, Seat> seatMap = seats.stream()
                .collect(Collectors.toMap(Seat::getSeatNumber, s -> s));

        //save passengers
        List<Passenger> passengers = bookingReqDto.passengerList().stream().map(p -> {
            Passenger passenger = new Passenger();
            passenger.setName(p.name());
            passenger.setAge(p.age());
            passenger.setGender(p.gender());
            passenger.setBooking(booking);
            passenger.setSeat(seatMap.get(p.seatNumber()));
            return passenger;
        }).toList();

        passengerRepository.saveAll(passengers);

        return BookingMapper.mapToDto(booking);
    }

    public List<BookingResDto> getBookingsByUserId(String username) {
        User user = userService.findByUsername(username);
        List<Booking> list = bookingRepository.findByUserId(user.getUser_id());
        return list
                .stream()
                .map(BookingMapper::mapToDto)
                .toList();
    }

    public void cancelBooking(long bookingId, String username) {
        User user = userService.findByUsername(username);
        Booking booking = bookingRepository.findBookingByUserAndBookingId(bookingId,username);

        if(!booking.getUser().getAppUser().getUsername().equals(username)){
            throw new UnauthorizedAccessException("Can't cancel booking which doesn't belong to u");
        }

        // set booking status to CANCELLED
        booking.setBookingStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);

        //free up seats and set to available
        List<Passenger> passengers = passengerRepository.findPassengerByBookingId(bookingId);
        passengers.forEach(passenger -> {
            Seat seat = passenger.getSeat();
            seat.setSeatStatus(SeatStatus.AVAILABLE);
            seatRepository.save(seat);
        });

        //setting payment status for refund processing
        Payment payment = paymentService.findPaymentByBookingId(bookingId);
        payment.setStatus(PaymentStatus.REFUND_PROCESSING);
        paymentRepository.save(payment);
    }
}