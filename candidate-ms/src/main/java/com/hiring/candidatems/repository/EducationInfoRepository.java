package com.hiring.candidatems.repository;

import com.hiring.candidatems.domain.entity.EducationInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EducationInfoRepository extends JpaRepository<EducationInfo, UUID> {
    Optional<EducationInfo> findByCandidateId(UUID candidateId);

    boolean existsByCandidateId(UUID candidateId);

    void deleteByCandidateId(UUID candidateId);
}
