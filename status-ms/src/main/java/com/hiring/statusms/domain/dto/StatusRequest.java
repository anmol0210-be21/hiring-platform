package com.hiring.statusms.domain.dto;

import com.hiring.statusms.domain.enums.StatusType;

public record StatusRequest(
        StatusType statusType
) {
}
