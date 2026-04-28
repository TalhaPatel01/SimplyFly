package com.springboot.simplyfly.dto;

import java.util.List;

public record FlightResPageDto(
    List<FlightResDto> list,
    long totalRecords,
    long totalPages
) {
}