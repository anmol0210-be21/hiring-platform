package com.hiring.statusms.domain.entity;

import com.hiring.statusms.domain.enums.StatusType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
@Table(name = "status", uniqueConstraints = {
        @UniqueConstraint(columnNames = "candidateId")
})
public class Status {
    @Id
    @GeneratedValue(generator = "uuid")
    private UUID id;

    @NotNull(message = "Candidate ID is required")
    private UUID candidateId;

    @NotNull(message = "Status type is required")
    @Enumerated(EnumType.STRING)
    private StatusType statusType;
}
