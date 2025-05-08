package com.hiring.statusms.domain.dto;

import com.hiring.statusms.domain.enums.StatusType;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public record StatusResponse(
        UUID id,
        UUID candidateId,
        StatusType statusType,
        LocalDateTime createdDate,
        LocalDateTime lastModifiedDate
) implements Serializable {
}
