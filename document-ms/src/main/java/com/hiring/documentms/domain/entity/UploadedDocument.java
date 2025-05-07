package com.hiring.documentms.domain.entity;

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

    private UUID candidateId;
    private List<FileMetadata> files;

    public UploadedDocument() {
        this.id = UUID.randomUUID();
    }


    @Data
    public static class FileMetadata {
        private String fileName;
        private String fileType;
        private String fileId;
        private long size;
    }
}
