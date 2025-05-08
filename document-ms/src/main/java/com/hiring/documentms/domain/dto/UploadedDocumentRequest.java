package com.hiring.documentms.domain.dto;

import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public record UploadedDocumentRequest(
        @NotNull(message = "Candidate ID is required")
        UUID candidateId,

        @NotEmpty(message = "At least one file must be uploaded")
        List<@NotNull(message = "File must not be null") MultipartFile> files
) {
}
