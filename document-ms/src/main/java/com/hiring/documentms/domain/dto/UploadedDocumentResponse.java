package com.hiring.documentms.domain.dto;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public record UploadedDocumentResponse(
        String id,
        UUID candidateId,
        LocalDateTime createdDate,
        LocalDateTime lastModifiedDate,
        List<FileMetadataResponse> files
) {

    public record FileMetadataResponse(
            String fileName,
            String fileType,
            String fileId,
            long size
    ) {
    }
}
