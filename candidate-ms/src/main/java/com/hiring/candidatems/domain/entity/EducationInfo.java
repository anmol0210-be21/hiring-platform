package com.hiring.candidatems.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.util.UUID;

@Entity
@Data
public class EducationInfo {
    @Id
    @GeneratedValue(generator = "uuid")
    private UUID id;

    @OneToOne
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;

    private String degree;
    private String institution;
    private Date yearOfPassing;
}
