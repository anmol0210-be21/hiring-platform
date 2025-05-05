package com.hiring.authms.domain.dto;

import java.io.Serializable;

public record AuthResponse(
        String token
) implements Serializable {
}
