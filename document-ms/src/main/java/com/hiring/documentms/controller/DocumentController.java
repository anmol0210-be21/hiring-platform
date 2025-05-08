package com.hiring.documentms.controller;

import com.hiring.documentms.domain.dto.UploadedDocumentRequest;
import com.hiring.documentms.domain.dto.UploadedDocumentResponse;
import com.hiring.documentms.service.DocumentService;
import jakarta.validation.Valid;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {
    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }


    @PostMapping(value = "/upload/{candidateId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UploadedDocumentResponse> uploadDocuments(
            @Valid @PathVariable UUID candidateId,
            @RequestParam("files") List<MultipartFile> files) throws IOException {
        
        if (files.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        
        UploadedDocumentResponse response = documentService.uploadDocuments(
                new UploadedDocumentRequest(candidateId, files)
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{candidateId}")
    public ResponseEntity<UploadedDocumentResponse> getDocumentsByCandidateId(@Valid @PathVariable UUID candidateId) {
        Optional<UploadedDocumentResponse> response = documentService.getDocumentsByCandidateId(candidateId);
        return response.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<InputStreamResource> downloadDocument(@Valid @PathVariable String fileId) throws IOException {
        return documentService.downloadDocument(fileId);
    }
}