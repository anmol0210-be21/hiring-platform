package com.hiring.candidatems.service.producer;

import com.hiring.candidatems.domain.dto.HiringStatusMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class StatusProducerService {
    private final RabbitTemplate rabbitTemplate;

    public StatusProducerService(final RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessageToStatusMS(final HiringStatusMessage message) {
        rabbitTemplate.convertAndSend(message.exchange(), message.routingKey(), message);
    }
}
