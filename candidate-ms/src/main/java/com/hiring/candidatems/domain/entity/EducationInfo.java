package com.hiring.candidatems.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.sql.Date;
import java.util.UUID;

@Entity
@Data
@Table(name = "education_info", uniqueConstraints = {
        @UniqueConstraint(columnNames = "candidate_id")
})
public class EducationInfo {
    @Id
    @GeneratedValue(generator = "uuid")
    private UUID id;

    @OneToOne
    @JoinColumn(name = "candidate_id", nullable = false, unique = true)
    @NotNull(message = "Candidate must be associated")
    private Candidate candidate;

    @NotBlank(message = "Degree must not be blank")
    @Size(max = 100)
    private String degree;

    @NotBlank(message = "Institution must not be blank")
    @Size(max = 150)
    private String institution;

    @NotNull(message = "Year of passing is required")
    @PastOrPresent
    private Date yearOfPassing;
}
