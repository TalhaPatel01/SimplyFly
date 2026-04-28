package com.springboot.simplyfly.mapper;

import com.springboot.simplyfly.dto.SeatResDto;
import com.springboot.simplyfly.model.Seat;

public class SeatMapper {
    public static SeatResDto mapToDto(Seat seat){
        return new SeatResDto(
                seat.getRowNumber(),
                seat.getColumnName(),
                seat.getSeatNumber(),
                seat.getSeatStatus(),
                seat.getSeatType(),
                seat.getSeatClass()
        );
    }
}