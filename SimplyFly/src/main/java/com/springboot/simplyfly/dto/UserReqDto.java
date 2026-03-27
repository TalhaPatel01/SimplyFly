package com.springboot.simplyfly.dto;

public record UserReqDto(
        String name,
        String email,
        String phone_no,
        String password
) {
}