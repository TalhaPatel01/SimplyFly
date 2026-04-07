package com.springboot.simplyfly.dto;

import java.time.LocalDate;

public record RouteFlightSearchReqDto(
        String source,
        String destination,
        LocalDate date
) {
}