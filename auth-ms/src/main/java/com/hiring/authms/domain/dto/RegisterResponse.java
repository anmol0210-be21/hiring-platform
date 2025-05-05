package com.hiring.authms.domain.dto;

public record RegisterResponse(
        Boolean success,
        String token
) {
}
