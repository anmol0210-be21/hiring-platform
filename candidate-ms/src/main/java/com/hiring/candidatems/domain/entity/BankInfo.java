package com.hiring.candidatems.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "bank_info", uniqueConstraints = {
        @UniqueConstraint(columnNames = "candidate_id")
})
@EntityListeners(AuditingEntityListener.class)
public class BankInfo {
    @Id
    @GeneratedValue(generator = "uuid")
    private UUID id;

    @OneToOne
    @JoinColumn(name = "candidate_id", nullable = false, unique = true)
    @NotNull(message = "Candidate must be associated")
    private Candidate candidate;

    @NotBlank(message = "Bank name must not be blank")
    @Size(max = 100)
    private String bankName;

    @NotBlank(message = "Account number must not be blank")
    @Pattern(regexp = "\\d{9,18}")
    private String accountNumber;

    @NotBlank(message = "IFSC code must not be blank")
    @Pattern(regexp = "^[A-Z]{4}0[A-Z0-9]{6}$")
    private String ifscCode;


    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;
}
