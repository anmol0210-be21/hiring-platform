package com.hiring.candidatems.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class BankInfo {
    @Id
    @GeneratedValue(generator = "uuid")
    private UUID id;

    @OneToOne
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;

    private String bankName;
    private String accountNumber;
    private String ifscCode;
}
