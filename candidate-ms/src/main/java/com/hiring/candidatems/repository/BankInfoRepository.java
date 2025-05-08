package com.hiring.candidatems.repository;

import com.hiring.candidatems.domain.entity.BankInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BankInfoRepository extends JpaRepository<BankInfo, UUID> {
    Optional<BankInfo> findByCandidateId(UUID candidateId);

    boolean existsByCandidateId(UUID candidateId);

    void deleteByCandidateId(UUID candidateId);
}
