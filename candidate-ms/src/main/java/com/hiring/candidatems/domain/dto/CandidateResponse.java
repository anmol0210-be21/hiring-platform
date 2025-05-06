package com.hiring.candidatems.domain.dto;

import java.sql.Date;
import java.util.UUID;

public record CandidateResponse(
        UUID id,
        String firstName,
        String lastName,
        String email,
        String phone,
        Date dateOfBirth,
        String gender,
        String city
) {
}
