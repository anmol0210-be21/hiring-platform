package com.hiring.notificationms.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class OfferNotification {
    @Id
    @GeneratedValue(generator = "uuid")
    private UUID id;

    @NotNull(message = "Candidate ID is required")
    private UUID candidateId;

    @PastOrPresent(message = "Sent time cannot be in the future")
    private LocalDateTime sentAt;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    @PrePersist
    public void prePersist() {
        this.sentAt = LocalDateTime.now();
    }
}
