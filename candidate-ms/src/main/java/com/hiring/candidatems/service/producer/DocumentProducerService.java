package com.hiring.candidatems.service.producer;

import com.hiring.candidatems.domain.dto.DocumentMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class DocumentProducerService {
    private final RabbitTemplate rabbitTemplate;

    public DocumentProducerService(final RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessageToDocumentMS(DocumentMessage message) {
        rabbitTemplate.convertAndSend(message.exchange(), message.routingKey(), message);
    }
}
