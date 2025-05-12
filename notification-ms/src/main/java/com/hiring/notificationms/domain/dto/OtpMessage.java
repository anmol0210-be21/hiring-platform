package com.hiring.notificationms.domain.dto;

public record OtpMessage(
        String exchange,
        String routingKey,
        String email,
        String otp
) {
}
