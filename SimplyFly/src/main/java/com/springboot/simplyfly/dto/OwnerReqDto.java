package com.springboot.simplyfly.dto;

public record OwnerReqDto(
        String airline_name,
        String email,
        String phone,
        String password
) {
}