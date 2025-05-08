package com.hiring.statusms.domain.dto;

import com.hiring.statusms.domain.enums.StatusType;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.UUID;

public record StatusRequest(
        @NotNull(message = "Candidate ID is required")
        UUID candidateId,

        @NotNull(message = "Status type is required")
        StatusType statusType
) implements Serializable {
}
