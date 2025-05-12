package com.hiring.authms.service.producer;

import com.hiring.authms.domain.dto.OtpMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class OtpProducerService {
    private final RabbitTemplate rabbitTemplate;

    public OtpProducerService(final RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessageToNotificationMS(OtpMessage otpMessage) {
        rabbitTemplate.convertAndSend(otpMessage.exchange(), otpMessage.routingKey(), otpMessage);
    }
}
