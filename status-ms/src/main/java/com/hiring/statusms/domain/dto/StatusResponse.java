package com.hiring.statusms.domain.dto;

import com.hiring.statusms.domain.enums.StatusType;

import java.io.Serializable;
import java.util.UUID;

public record StatusResponse(
        UUID id,
        StatusType statusType
) implements Serializable {
}
