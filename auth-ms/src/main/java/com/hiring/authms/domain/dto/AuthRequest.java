package com.hiring.authms.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

public record AuthRequest(
        @NotBlank(message = "Username must not be blank")
        @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
        String username,

        @NotBlank(message = "Password must not be blank")
        @Size(min = 6, max = 30, message = "Password must be between 6 and 30 characters")
        String password
) implements Serializable {
}
