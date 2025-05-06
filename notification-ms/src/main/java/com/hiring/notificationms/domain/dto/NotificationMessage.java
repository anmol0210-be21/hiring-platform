package com.hiring.notificationms.domain.dto;

import com.hiring.notificationms.domain.enums.StatusType;

import java.util.UUID;

public record NotificationMessage(
        String exchange,
        String routingKey,
        UUID statusId,
        UUID candidateId,
        StatusType statusType
) {
}
