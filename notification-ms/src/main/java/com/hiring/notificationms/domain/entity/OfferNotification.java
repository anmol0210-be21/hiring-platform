package com.hiring.notificationms.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
public class OfferNotification {
    @Id
    @GeneratedValue(generator = "uuid")
    private UUID id;

    private UUID candidateId;
    private LocalDateTime sentAt;


    @PrePersist
    public void prePersist() {
        this.sentAt = LocalDateTime.now();
    }
}


