package com.hiring.candidatems.repository;

import com.hiring.candidatems.domain.entity.BankInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BankInfoRepository extends JpaRepository<BankInfo, UUID> {
}
