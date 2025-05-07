package com.hiring.documentms.domain.dto;

import java.util.List;
import java.util.UUID;

public record UploadedDocumentResponse(
        UUID id,
        UUID candidateId,
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
