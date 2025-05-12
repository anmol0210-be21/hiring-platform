package com.hiring.authms.domain.dto;

public record VerifyOtpResponse(
        boolean success,
        String token
) {
}
