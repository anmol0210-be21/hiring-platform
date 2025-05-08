package com.hiring.documentms.domain.entity;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Data
@Document(collection = "uploaded_document")
public class UploadedDocument {
    @Id
    private UUID id;

    @NotNull(message = "Candidate ID is required")
    private UUID candidateId;

    @NotEmpty(message = "At least one file must be uploaded")
    private List<@Valid FileMetadata> files;

    public UploadedDocument() {
        this.id = UUID.randomUUID();
    }

    @Data
    public static class FileMetadata {
        @NotBlank(message = "File name must not be blank")
        private String fileName;

        @NotBlank(message = "File type must not be blank")
        private String fileType;

        @NotBlank(message = "File ID must not be blank")
        private String fileId;

        @Positive(message = "File size must be greater than zero")
        private long size;
    }
}
