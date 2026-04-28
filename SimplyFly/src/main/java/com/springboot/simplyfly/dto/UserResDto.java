package com.springboot.simplyfly.dto;

public record UserResDto(
    long id,
    String username,
    String name,
    String email,
    String phone_no
) {
}