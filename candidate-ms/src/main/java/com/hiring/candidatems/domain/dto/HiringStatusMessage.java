package com.hiring.candidatems.domain.dto;

import java.io.Serializable;
import java.util.UUID;

public record HiringStatusMessage(
        String exchange,
        String routingKey,
        UUID candidateId
) implements Serializable {
}
