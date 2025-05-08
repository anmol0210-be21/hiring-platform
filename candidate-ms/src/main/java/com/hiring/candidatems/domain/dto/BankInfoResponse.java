package com.hiring.candidatems.domain.dto;

import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.UUID;

public record BankInfoResponse(
        UUID id,
        UUID candidateId,
        String bankName,
        String accountNumber,
        String ifscCode,
        LocalDateTime createdDate,
        LocalDateTime lastModifiedDate
) {
}
