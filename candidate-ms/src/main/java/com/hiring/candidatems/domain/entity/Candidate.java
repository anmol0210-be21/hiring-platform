package com.hiring.candidatems.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.sql.Date;
import java.util.UUID;

@Entity
@Data
public class Candidate {
    @Id
    @GeneratedValue(generator = "uuid")
    private UUID id;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @Email(message = "Email should be valid")
    private String email;

    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Invalid phone number")
    private String phone;

    @Past(message = "Date of birth must be in the past")
    private Date dateOfBirth;

    @NotBlank(message = "Gender is required")
    private String gender;

    @NotBlank(message = "City is required")
    private String city;
}

