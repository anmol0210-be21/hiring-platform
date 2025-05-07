package com.hiring.candidatems.domain.dto;

import java.sql.Date;
import java.util.UUID;

public record EducationInfoResponse(
        UUID id,
        UUID candidateId,
        String degree,
        String institution,
        Date yearOfPassing
) {
}
