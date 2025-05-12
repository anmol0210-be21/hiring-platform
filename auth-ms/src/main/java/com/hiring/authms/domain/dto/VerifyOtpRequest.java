package com.hiring.authms.domain.dto;

public record VerifyOtpRequest(
        String email,
        String otp
) {
}
