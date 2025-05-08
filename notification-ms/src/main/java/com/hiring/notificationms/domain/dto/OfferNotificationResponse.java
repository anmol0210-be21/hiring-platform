package com.hiring.notificationms.domain.dto;


import java.time.LocalDateTime;
import java.util.UUID;

public record OfferNotificationResponse (
        UUID id,
        UUID candidateId,
        LocalDateTime sentAt,
        LocalDateTime createdDate,
        LocalDateTime lastModifiedDate
) {
}
