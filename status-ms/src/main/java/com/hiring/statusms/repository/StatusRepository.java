package com.hiring.statusms.repository;

import com.hiring.statusms.domain.entity.Status;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface StatusRepository extends MongoRepository<Status, UUID> {
    Optional<Status> findByCandidateId(UUID candidateId);

    boolean existsByCandidateId(UUID candidateId);

    void deleteByCandidateId(UUID candidateId);
}
