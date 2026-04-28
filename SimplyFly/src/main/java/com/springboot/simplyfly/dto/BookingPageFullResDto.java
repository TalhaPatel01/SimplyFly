package com.springboot.simplyfly.dto;

import java.util.List;

public record BookingPageFullResDto(
        List<BookingFullResDto> list,
        long totalRecords,
        long totalPages
) {
}