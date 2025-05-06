package com.hiring.notificationms.service.listener;

import com.hiring.notificationms.domain.dto.NotificationMessage;
import com.hiring.notificationms.service.NotificationService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationListenerService {
    private final NotificationService notificationService;

    public NotificationListenerService(final NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @RabbitListener(queues = "hiringNotificationQueue")
    public void receive(final NotificationMessage message) {
        if (message.routingKey().equals("notification.offered")) {
            notificationService.sendJobOffer(message);
        }
    }
}
