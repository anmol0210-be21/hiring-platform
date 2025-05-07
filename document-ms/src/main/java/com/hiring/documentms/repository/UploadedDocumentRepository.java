package com.hiring.documentms.repository;

import com.hiring.documentms.domain.entity.UploadedDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UploadedDocumentRepository extends MongoRepository<UploadedDocument, UUID> {
    Optional<UploadedDocument> findByCandidateId(UUID candidateId);
}
