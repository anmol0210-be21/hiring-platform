package com.hiring.statusms.domain.dto;

import java.io.Serializable;
import java.util.UUID;

public record HiringStatusMessage(
        String exchange,
        String routingKey,
        UUID candidateId
) implements Serializable {
}