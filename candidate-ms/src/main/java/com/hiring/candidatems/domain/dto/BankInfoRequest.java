package com.hiring.candidatems.domain.dto;

import java.util.UUID;

public record BankInfoRequest(
        UUID candidateId,
        String bankName,
        String accountNumber,
        String ifscCode
) {
}
