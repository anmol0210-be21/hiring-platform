package com.hiring.documentms.service.listener;

import com.hiring.documentms.domain.dto.DocumentMessage;
import com.hiring.documentms.service.DocumentService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class DocumentListenerService {
    private final DocumentService documentService;

    public DocumentListenerService(final DocumentService documentService) {
        this.documentService = documentService;
    }

    @RabbitListener(queues = "hiringDocumentQueue")
    public void receive(DocumentMessage message) {
        if (message.routingKey().equals("document.init")) {
            documentService.addDocument(message.candidateId());
        }
    }
}
