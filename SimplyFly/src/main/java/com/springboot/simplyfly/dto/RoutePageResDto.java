package com.springboot.simplyfly.dto;

import java.util.List;

public record RoutePageResDto(
        List<RouteResDto> list,
        long totalRecords,
        long totalPages
) {
}