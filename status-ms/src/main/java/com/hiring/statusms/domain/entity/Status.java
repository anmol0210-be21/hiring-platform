package com.hiring.statusms.domain.entity;

import com.hiring.statusms.domain.enums.StatusType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "status", uniqueConstraints = {
        @UniqueConstraint(columnNames = "candidateId")
})
@EntityListeners(AuditingEntityListener.class)
public class Status {
    @Id
    @GeneratedValue(generator = "uuid")
    private UUID id;

    @NotNull(message = "Candidate ID is required")
    private UUID candidateId;

    @NotNull(message = "Status type is required")
    @Enumerated(EnumType.STRING)
    private StatusType statusType;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;
}
