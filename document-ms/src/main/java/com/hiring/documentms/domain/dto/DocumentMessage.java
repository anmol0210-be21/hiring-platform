package com.hiring.documentms.domain.dto;

import java.io.Serializable;
import java.util.UUID;

public record DocumentMessage(
        String exchange,
        String routingKey,
        UUID candidateId
) implements Serializable {
}
