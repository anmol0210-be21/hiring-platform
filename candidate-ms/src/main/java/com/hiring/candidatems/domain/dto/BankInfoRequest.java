package com.hiring.candidatems.domain.dto;

import jakarta.validation.constraints.*;

import java.util.UUID;

public record BankInfoRequest(
        @NotNull(message = "Candidate ID is required")
        UUID candidateId,

        @NotBlank(message = "Bank name must not be blank")
        @Size(max = 100, message = "Bank name must not exceed 100 characters")
        String bankName,

        @NotBlank(message = "Account number must not be blank")
        @Pattern(regexp = "\\d{9,18}", message = "Account number must be between 9 to 18 digits")
        String accountNumber,

        @NotBlank(message = "IFSC code must not be blank")
        @Pattern(regexp = "^[A-Z]{4}0[A-Z0-9]{6}$", message = "Invalid IFSC code format")
        String ifscCode
) {}
