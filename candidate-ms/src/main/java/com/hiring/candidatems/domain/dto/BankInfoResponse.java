package com.hiring.candidatems.domain.dto;

import java.util.UUID;

public record BankInfoResponse(
        UUID id,
        UUID candidateId,
        String bankName,
        String accountNumber,
        String ifscCode
) {
}
