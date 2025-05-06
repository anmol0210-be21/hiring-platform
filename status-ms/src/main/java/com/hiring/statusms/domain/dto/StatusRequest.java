package com.hiring.statusms.domain.dto;

import com.hiring.statusms.domain.enums.StatusType;

import java.io.Serializable;

public record StatusRequest(
        StatusType statusType
) implements Serializable {
}
