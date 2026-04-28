package com.springboot.simplyfly.dto;

import com.springboot.simplyfly.enums.PaymentMethod;

public record PaymentDto(
        PaymentMethod paymentMethod,
        long bookingId
) {
}