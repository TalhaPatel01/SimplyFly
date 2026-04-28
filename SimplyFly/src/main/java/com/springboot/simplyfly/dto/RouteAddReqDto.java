package com.springboot.simplyfly.dto;

public record RouteAddReqDto(
        String sourceCode,
        String destinationCode,
        double distance
) {
}