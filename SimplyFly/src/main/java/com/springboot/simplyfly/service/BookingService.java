package com.springboot.simplyfly.service;

import com.springboot.simplyfly.dto.*;
import com.springboot.simplyfly.enums.*;
import com.springboot.simplyfly.exception.InvalidSeatSelected;
import com.springboot.simplyfly.exception.ResourceNotFoundException;
import com.springboot.simplyfly.exception.SeatsNotAvailableException;
import com.springboot.simplyfly.exception.UnauthorizedAccessException;
import com.springboot.simplyfly.mapper.PassengerMapper;
import com.springboot.simplyfly.model.*;
import com.springboot.simplyfly.repository.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.event.Level;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class BookingService {
    private final RouteFlightRepository routeFlightRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final UserService userService;
    private final PassengerRepository passengerRepository;
    private final SeatRepository seatRepository;
    private final PaymentService paymentService;
    private final PaymentRepository paymentRepository;
    private final TicketRepository ticketRepository;

    public BookingResDto createBooking(BookingReqDto bookingReqDto, String username) {
        log.atLevel(Level.INFO).log("Called Create booking API");

        User user = userService.findByUsername(username);

        RouteFlight routeFlight = routeFlightRepository.findById(bookingReqDto.routeFlightId())
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found"));

        //ONLY NON-INFANT seats
        List<String> seatNumbers = bookingReqDto.passengerList()
                .stream()
                .filter(p -> p.passengerType() != PassengerType.INFANT)
                .map(PassengerDto::seatNumber)
                .toList();

        //fetching seats
        List<Seat> seats = seatRepository
                .findByRouteFlightIdAndSeatNumberIn(
                        bookingReqDto.routeFlightId(),
                        seatNumbers
                );

        if (seats.size() != seatNumbers.size()) {
            throw new InvalidSeatSelected("Invalid seat selected");
        }

        for (Seat seat : seats) {
            if (seat.getSeatStatus() != SeatStatus.AVAILABLE) {
                throw new SeatsNotAvailableException("Selected seat is already booked");
            }
        }

        int updatedRows = routeFlightRepository.updateSeats(
                bookingReqDto.routeFlightId(),
                seatNumbers.size()
        );

        if (updatedRows == 0) {
            throw new SeatsNotAvailableException("Not enough seats available");
        }

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
        booking.setSeatClass(bookingReqDto.seatClass());

        // passenger-type based pricing
        double totalAmount = 0;
        double baseFare = 0;
        if(bookingReqDto.seatClass().equals(SeatClass.ECONOMY)){
            baseFare += routeFlight.getFare();
        }
        if(bookingReqDto.seatClass().equals(SeatClass.PREMIUM_ECONOMY)){
            baseFare += routeFlight.getPremiumEconomyFare();
        }
        if(bookingReqDto.seatClass().equals(SeatClass.BUSINESS)){
            baseFare += routeFlight.getBusinessClassFare();
        }
        if(bookingReqDto.seatClass().equals(SeatClass.FIRST_CLASS)){
            baseFare += routeFlight.getFirstClassFare();
        }

        for (PassengerDto p : bookingReqDto.passengerList()) {

            if (p.passengerType() == PassengerType.ADULT) {
                totalAmount += baseFare;
            }
            else if (p.passengerType() == PassengerType.CHILD) {
                totalAmount += baseFare * routeFlight.getChildFactor();
            }
            else if (p.passengerType() == PassengerType.INFANT) {
                totalAmount += baseFare * routeFlight.getInfantFactor();
            }
        }

        booking.setTotalAmount(totalAmount);
        booking.setBookingStatus(BookingStatus.PENDING);

        bookingRepository.save(booking);

        Map<String, Seat> seatMap = seats.stream()
                .collect(Collectors.toMap(Seat::getSeatNumber, s -> s));

        // save passengers
        List<Passenger> passengers = bookingReqDto.passengerList().stream().map(p -> {

            Passenger passenger = new Passenger();
            passenger.setName(p.name());
            passenger.setAge(p.age());
            passenger.setGender(p.gender());
            passenger.setPassengerType(p.passengerType());
            passenger.setBooking(booking);

            //INFANT → NO SEAT
            if (p.passengerType() == PassengerType.INFANT) {
                passenger.setSeat(null);
            } else {
                passenger.setSeat(seatMap.get(p.seatNumber()));
            }

            return passenger;

        }).toList();

        passengerRepository.saveAll(passengers);

        log.atLevel(Level.INFO).log("Booking created.....");

        List<PassengerDetailsDto> list = passengers
                .stream()
                .map(PassengerMapper::mapToDto)
                .toList();

        return new BookingResDto(
                booking.getId(),
                booking.getBookingStatus(),
                booking.getTotalAmount(),
                list
        );
    }

    public List<UserBookingResDto> getBookingsByUserId(String username) {
        User user = userService.findByUsername(username);
        List<Booking> bookings = bookingRepository.findByUserId(user.getUser_id());
        List<UserBookingResDto> list = new ArrayList<>();

        for(Booking booking: bookings){
            UserBookingResDto dto = new UserBookingResDto(
                    booking.getId(),
                    booking.getFlight().getRoute().getSourceAirport().getCity(),
                    booking.getFlight().getRoute().getDestinationAirport().getCity(),
                    booking.getFlight().getFlight().getOwner().getAirline_name(),
                    booking.getFlight().getFlight().getFlightNumber(),
                    booking.getDepartureTime(),
                    booking.getArrivalTime(),
                    booking.getTravelDate(),
                    passengerRepository.countByBookingId(booking.getId()),
                    booking.getBookingStatus()
            );
            list.add(dto);
        }
        return list;
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

        //set ticket status to cancelled
        Ticket ticket = ticketRepository.findTicketByBooking(booking);
        ticket.setTicketStatus(TicketStatus.CANCELLED);
        ticketRepository.save(ticket);

        //setting payment status for refund processing
        Payment payment = paymentService.findPaymentByBookingId(bookingId);
        payment.setStatus(PaymentStatus.REFUND_PROCESSING);
        paymentRepository.save(payment);
    }

    public BookingFullResDto getBookingById(long bookingId, String username) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        Flight flight = booking.getFlight().getFlight();

        List<PassengerDto> passengers = passengerRepository
                .findPassengerByBookingId(bookingId)
                .stream()
                .map(p -> new PassengerDto(
                        p.getName(),
                        p.getAge(),
                        p.getGender(),
                        p.getPassengerType(),
                        p.getSeat() != null ? p.getSeat().getSeatNumber() : null
                ))
                .toList();

        return new BookingFullResDto(
                booking.getId(),
                booking.getBookingStatus().toString(),
                booking.getTotalAmount(),
                booking.getFlight().getRoute().getSourceAirport().getCity(),
                booking.getFlight().getRoute().getDestinationAirport().getCity(),
                booking.getTravelDate().toString(),

                new FlightDto(
                        flight.getOwner().getAirline_name(),
                        flight.getFlightNumber(),
                        booking.getFlight().getDepartureTime(),
                        booking.getFlight().getArrivalTime()
                ),
                passengers
        );
    }

    public BookingPageFullResDto getBookingsByOwner(String username,int page,int size) {
        Pageable pageable = PageRequest.of(page,size);
        Page<Booking> bookings = bookingRepository.findByOwner(username,pageable);
        long totalRecords = bookings.getTotalElements();
        long totalPages = bookings.getTotalPages();

        List<BookingFullResDto> list =  bookings.stream().map(booking -> {
            Flight flight = booking.getFlight().getFlight();

            List<PassengerDto> passengers = passengerRepository
                    .findPassengerByBookingId(booking.getId())
                    .stream()
                    .map(p -> new PassengerDto(
                            p.getName(),
                            p.getAge(),
                            p.getGender(),
                            p.getPassengerType(),
                            p.getSeat() != null ? p.getSeat().getSeatNumber() : null
                    ))
                    .toList();

            return new BookingFullResDto(
                    booking.getId(),
                    booking.getBookingStatus().toString(),
                    booking.getTotalAmount(),
                    booking.getFlight().getRoute().getSourceAirport().getCity(),
                    booking.getFlight().getRoute().getDestinationAirport().getCity(),
                    booking.getTravelDate().toString(),

                    new FlightDto(
                            flight.getOwner().getAirline_name(),
                            flight.getFlightNumber(),
                            booking.getFlight().getDepartureTime(),
                            booking.getFlight().getArrivalTime()
                    ),
                    passengers
            );

        }).toList();

        return new BookingPageFullResDto(
                list,
                totalRecords,
                totalPages
        );
    }
}