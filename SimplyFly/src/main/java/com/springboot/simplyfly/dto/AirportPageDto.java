package com.springboot.simplyfly.dto;

import com.springboot.simplyfly.model.Airport;

import java.util.List;

public record AirportPageDto(
        List<Airport> list,
        long totalRecords,
        int totalPages
) {
}