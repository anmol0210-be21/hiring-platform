package com.hiring.statusms.service.listener;

import com.hiring.statusms.domain.dto.HiringStatusMessage;
import com.hiring.statusms.domain.dto.StatusRequest;
import com.hiring.statusms.domain.enums.StatusType;
import com.hiring.statusms.service.StatusService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class StatusConsumerListener {
    private final StatusService statusService;

    public StatusConsumerListener(final StatusService statusService) {
        this.statusService = statusService;
    }

    @RabbitListener(queues = "hiringStatusQueue")
    public void receiveMessage(HiringStatusMessage message) {
        if (message.routingKey().equals("status.init")) {
            statusService.add(
                    new StatusRequest(
                            message.candidateId(),
                            StatusType.APPLIED
                    )
            );
        }
    }
}
