package com.hiring.candidatems.domain.dto;

import jakarta.validation.constraints.*;
import java.sql.Date;

public record CandidateRequest(
        @NotBlank(message = "First name is required")
        String firstName,

        @NotBlank(message = "Last name is required")
        String lastName,

        @Email(message = "Email should be valid")
        String email,

        @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Invalid phone number")
        String phone,

        @Past(message = "Date of birth must be in the past")
        Date dateOfBirth,

        @NotBlank(message = "Gender is required")
        String gender,

        @NotBlank(message = "City is required")
        String city
) {
}
