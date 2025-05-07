package com.hiring.candidatems.domain.dto;

import java.sql.Date;
import java.util.UUID;

public record EducationInfoRequest(
        UUID candidateId,
        String degree,
        String institution,
        Date yearOfPassing
) {
}
