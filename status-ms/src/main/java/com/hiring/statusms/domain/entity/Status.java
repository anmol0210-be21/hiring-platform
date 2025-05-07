package com.hiring.statusms.domain.entity;

import com.hiring.statusms.domain.enums.StatusType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class Status {
    @Id
    @GeneratedValue(generator = "uuid")
    private UUID id;

    private UUID candidateId;

    private StatusType statusType;
}
