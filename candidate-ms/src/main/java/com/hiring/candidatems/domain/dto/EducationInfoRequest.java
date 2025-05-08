package com.hiring.candidatems.domain.dto;

import jakarta.validation.constraints.*;

import java.sql.Date;
import java.util.UUID;

public record EducationInfoRequest(
        @NotNull(message = "Candidate ID is required")
        UUID candidateId,

        @NotBlank(message = "Degree must not be blank")
        @Size(max = 100, message = "Degree name must not exceed 100 characters")
        String degree,

        @NotBlank(message = "Institution must not be blank")
        @Size(max = 150, message = "Institution name must not exceed 150 characters")
        String institution,

        @NotNull(message = "Year of passing is required")
        @PastOrPresent(message = "Year of passing must be in the past or present")
        Date yearOfPassing
) {}
