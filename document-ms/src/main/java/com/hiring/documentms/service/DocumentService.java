package com.hiring.documentms.service;

import com.hiring.documentms.domain.dto.UploadedDocumentRequest;
import com.hiring.documentms.domain.dto.UploadedDocumentResponse;
import com.hiring.documentms.domain.dto.UploadedDocumentResponse.FileMetadataResponse;
import com.hiring.documentms.domain.entity.UploadedDocument;
import com.hiring.documentms.domain.entity.UploadedDocument.FileMetadata;
import com.hiring.documentms.exception.DocumentException;
import com.hiring.documentms.repository.UploadedDocumentRepository;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DocumentService {
    private final GridFsTemplate gridFsTemplate;
    private final UploadedDocumentRepository uploadedDocumentRepository;

    public DocumentService(final GridFsTemplate gridFsTemplate,
                           final UploadedDocumentRepository uploadedDocumentRepository) {
        this.gridFsTemplate = gridFsTemplate;
        this.uploadedDocumentRepository = uploadedDocumentRepository;
    }

    public void addDocument(final UUID candidateId) {
        boolean exists = uploadedDocumentRepository.findByCandidateId(candidateId).isPresent();
        if (!exists) {
            UploadedDocument uploadedDocument = new UploadedDocument();
            uploadedDocument.setCandidateId(candidateId);
            uploadedDocument.setFiles(new ArrayList<>());
            uploadedDocumentRepository.save(uploadedDocument);
        }
    }

    public UploadedDocumentResponse uploadDocuments(UploadedDocumentRequest request) throws IOException {
        Optional<UploadedDocument> existingDocumentOpt = uploadedDocumentRepository
                .findByCandidateId(request.candidateId());

        if (existingDocumentOpt.isEmpty()) {
            throw new DocumentException("Document not initialized for candidate: " + request.candidateId());
        }

        UploadedDocument uploadedDocument = existingDocumentOpt.get();

        for (MultipartFile file : request.files()) {
            String fileId = uploadFile(file);

            FileMetadata metadata = new FileMetadata();
            metadata.setFileName(file.getOriginalFilename());
            metadata.setFileType(file.getContentType());
            metadata.setFileId(fileId);
            metadata.setSize(file.getSize());

            uploadedDocument.getFiles().add(metadata);
        }

        uploadedDocument = uploadedDocumentRepository.save(uploadedDocument);
        return mapToResponse(uploadedDocument);
    }

    public Optional<UploadedDocumentResponse> getDocumentsByCandidateId(UUID candidateId) {
        Optional<UploadedDocument> uploadedDocumentOpt = uploadedDocumentRepository.findByCandidateId(candidateId);
        return uploadedDocumentOpt.map(this::mapToResponse);
    }

    public ResponseEntity<InputStreamResource> downloadDocument(String fileId) throws IOException {
        GridFsResource resource = downloadFile(fileId);

        if (!resource.exists()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .contentType(MediaType.parseMediaType(resource.getContentType()))
                .body(new InputStreamResource(resource.getInputStream()));
    }

    private String uploadFile(MultipartFile file) throws IOException {
        InputStream fileInputStream = file.getInputStream();
        return gridFsTemplate.store(fileInputStream, file.getOriginalFilename(), file.getContentType()).toString();
    }

    private GridFsResource downloadFile(String fileId) throws IOException {
        GridFSFile gridFSFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(fileId)));
        return gridFsTemplate.getResource(gridFSFile);
    }

    private UploadedDocumentResponse mapToResponse(UploadedDocument uploadedDocument) {
        List<FileMetadataResponse> fileResponses = uploadedDocument.getFiles().stream()
                .map(file -> new FileMetadataResponse(
                        file.getFileName(),
                        file.getFileType(),
                        file.getFileId(),
                        file.getSize()))
                .collect(Collectors.toList());

        return new UploadedDocumentResponse(
                uploadedDocument.getId(),
                uploadedDocument.getCandidateId(),
                fileResponses
        );
    }
}
