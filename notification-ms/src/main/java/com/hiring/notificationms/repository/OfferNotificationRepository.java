package com.hiring.notificationms.repository;

import com.hiring.notificationms.domain.entity.OfferNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OfferNotificationRepository extends JpaRepository<OfferNotification, UUID> {
    Optional<List<OfferNotification>> findAllByCandidateId(UUID candidateId);
}
