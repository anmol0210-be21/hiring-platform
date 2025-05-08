package com.hiring.statusms.domain.dto;

import com.hiring.statusms.domain.enums.StatusType;

import java.util.UUID;

public record NotificationMessage(
        String exchange,
        String routingKey,
        UUID statusId,
        UUID candidateId,
        StatusType statusType,
        String jwtToken
) {
}
