package com.springboot.simplyfly.dto;

import java.util.List;

public record PassengerDetailsPageDto(
        List<PassengerDetailsDto> list,
        long totalRecords,
        long totalPages
) {
}