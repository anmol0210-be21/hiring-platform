package com.hiring.documentms.domain.dto;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public record UploadedDocumentRequest(
        UUID candidateId,
        List<MultipartFile> files
) {
}
