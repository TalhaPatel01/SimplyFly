package com.springboot.simplyfly.dto;

public record OwnerResDto(
        long id,
        String airline_name,
        String email,
        String phone
) {
}
