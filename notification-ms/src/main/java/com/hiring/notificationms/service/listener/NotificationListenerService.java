package com.hiring.notificationms.service.listener;

import com.hiring.notificationms.security.JwtContextHolder;
import com.hiring.notificationms.domain.dto.NotificationMessage;
import com.hiring.notificationms.service.OfferNotificationService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationListenerService {
    private final OfferNotificationService offerNotificationService;

    public NotificationListenerService(final OfferNotificationService offerNotificationService) {
        this.offerNotificationService = offerNotificationService;
    }

    @RabbitListener(queues = "hiringNotificationQueue")
    public void receive(final NotificationMessage message, Message msg) {
        String token = (String) msg.getMessageProperties().getHeaders().get("Authorization");
        try{
            JwtContextHolder.setToken(token.replace("Bearer ", ""));

            if (message.routingKey().equals("notification.offered")) {
                offerNotificationService.sendJobOffer(message);
            }
        }
        finally {
            JwtContextHolder.clear();
        }
    }
}
