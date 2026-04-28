package com.springboot.simplyfly.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record OwnerSignUpDto(
        @NotNull
        @NotBlank
        String airline_name,
        @Email
        String email,
        String phone,
        @NotBlank
        @NotNull
        @Size(min = 3, max = 15)
        String username,
        String password
) {
}