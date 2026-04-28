package com.springboot.simplyfly.dto;

import com.springboot.simplyfly.enums.SeatClass;
import com.springboot.simplyfly.enums.SeatStatus;
import com.springboot.simplyfly.enums.SeatType;

public record SeatResDto(
        int row,
        String column,
        String seatNumber,
        SeatStatus seatStatus,
        SeatType seatType,
        SeatClass seatClass
) {
}