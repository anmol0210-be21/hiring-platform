package com.hiring.statusms.service.producer;

import com.hiring.statusms.domain.dto.NotificationMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationProducerService {
    private final RabbitTemplate rabbitTemplate;

    public NotificationProducerService(final RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessageToNotificationMS(NotificationMessage message) {
        rabbitTemplate.convertAndSend(message.exchange(), message.routingKey(), message, msg -> {
            msg.getMessageProperties().setHeader("Authorization", "Bearer " + message.jwtToken());
            return msg;
        });
    }
}
