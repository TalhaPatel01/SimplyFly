package com.springboot.simplyfly.dto;

public record OwnerResDto(
        long id,
        String username,
        String airline_name,
        String email,
        String phone
) {
}