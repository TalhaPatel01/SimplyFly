package com.springboot.simplyfly.mapper;

import com.springboot.simplyfly.dto.BookingResDto;
import com.springboot.simplyfly.model.Booking;

public class BookingMapper {
    public static BookingResDto mapToDto(Booking booking){
        return new BookingResDto(
                booking.getId(),
                booking.getBookingStatus(),
                booking.getTotalAmount()
        );
    }
}
