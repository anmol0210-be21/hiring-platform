package com.hiring.notificationms.domain.dto;

import java.util.UUID;

public record JobOfferEmail(
        UUID candidateId,
        String to,
        String subject,
        String candidateName,
        String position,
        String startDate,
        String salary,
        String location,
        String benefits,
        String companyEmail,
        String companyWebsite,
        String companyName
) {
}
