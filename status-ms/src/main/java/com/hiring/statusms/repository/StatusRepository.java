package com.hiring.statusms.repository;

import com.hiring.statusms.domain.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface StatusRepository extends JpaRepository<Status, UUID> {
    Optional<Status> findByCandidateId(UUID candidateId);

    boolean existsByCandidateId(UUID candidateId);

    void deleteByCandidateId(UUID candidateId);
}
