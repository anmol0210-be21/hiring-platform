package com.hiring.notificationms.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "offer_notification", uniqueConstraints = {
        @UniqueConstraint(columnNames = "candidateId")
})
public class OfferNotification {
    @Id
    @GeneratedValue(generator = "uuid")
    private UUID id;

    @NotNull(message = "Candidate ID is required")
    private UUID candidateId;

    @PastOrPresent(message = "Sent time cannot be in the future")
    private LocalDateTime sentAt;

    @PrePersist
    public void prePersist() {
        this.sentAt = LocalDateTime.now();
    }
}
