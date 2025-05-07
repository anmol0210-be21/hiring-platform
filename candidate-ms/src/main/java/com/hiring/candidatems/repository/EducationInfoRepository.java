package com.hiring.candidatems.repository;

import com.hiring.candidatems.domain.entity.EducationInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EducationInfoRepository extends JpaRepository<EducationInfo, UUID> {
}
