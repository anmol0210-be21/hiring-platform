package com.hiring.notificationms.service.listener;

import com.hiring.notificationms.domain.dto.OtpMessage;
import com.hiring.notificationms.service.OtpMessageService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class OtpListenerService {
    private final OtpMessageService otpMessageService;

    public OtpListenerService(final OtpMessageService otpMessageService) {
        this.otpMessageService = otpMessageService;
    }

    @RabbitListener(queues = "hiringAuthOtpQueue")
    public void receive(final OtpMessage message) {
        if (message.routingKey().equals("auth.otp")){
            otpMessageService.sendOtpEmail(message);
        }
    }
}
