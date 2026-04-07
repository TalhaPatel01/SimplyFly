package com.springboot.simplyfly.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserSignUpDto(
        @NotBlank
        String name,
        @Email
        String email,
        String phone_no,
        @NotBlank
        @NotNull
        @Size(min = 3, max = 15)
        String username,
        String password
) {
}