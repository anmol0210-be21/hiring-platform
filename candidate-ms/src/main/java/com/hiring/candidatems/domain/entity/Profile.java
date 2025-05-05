package com.hiring.candidatems.domain.entity;

import com.hiring.candidatems.domain.enums.HiringStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Entity
@Data
public class Profile {
    @Id
    @GeneratedValue(generator = "uuid")
    private UUID id;

    @Enumerated(EnumType.STRING)
    private HiringStatus hiringStatus;

    @OneToOne
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Document> documents;

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Interview> interviews;
}
