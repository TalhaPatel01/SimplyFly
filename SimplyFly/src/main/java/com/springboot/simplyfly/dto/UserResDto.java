package com.springboot.simplyfly.dto;

public record UserResDto(
    long id,
    String name,
    String email,
    String phone_no
) {
}