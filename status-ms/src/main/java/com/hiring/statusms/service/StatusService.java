package com.hiring.statusms.service;

import com.hiring.statusms.domain.dto.NotificationMessage;
import com.hiring.statusms.domain.dto.StatusRequest;
import com.hiring.statusms.domain.dto.StatusResponse;
import com.hiring.statusms.domain.entity.Status;
import com.hiring.statusms.domain.enums.StatusType;
import com.hiring.statusms.domain.mapper.StatusMapper;
import com.hiring.statusms.exception.StatusException;
import com.hiring.statusms.repository.StatusRepository;
import com.hiring.statusms.service.producer.NotificationProducerService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class StatusService {
    private final StatusRepository statusRepository;
    private final StatusMapper statusMapper;
    private final NotificationProducerService notificationProducerService;

    public StatusService(final StatusRepository statusRepository,
                         final StatusMapper statusMapper,
                         final NotificationProducerService notificationProducerService) {
        this.statusRepository = statusRepository;
        this.statusMapper = statusMapper;
        this.notificationProducerService = notificationProducerService;
    }

    public List<StatusResponse> getAll() {
        return statusRepository.findAll().stream()
                .map(statusMapper::toResponse)
                .toList();
    }

    public StatusResponse getById(final UUID id) {
        return statusMapper.toResponse(
                statusRepository.findByCandidateId(id)
                        .orElseThrow(() -> new StatusException("Status for Candidate Id: " + id + " not found"))
        );
    }

    public StatusResponse add(final StatusRequest statusRequest) {
        Status status = statusMapper.toStatus(statusRequest);
        Status savedStatus = statusRepository.save(status);
        return statusMapper.toResponse(savedStatus);
    }

    public StatusResponse update(final StatusRequest statusRequest, final UUID id, HttpServletRequest request) {
        Status currentStatus = statusRepository.findByCandidateId(id)
                .orElseThrow(() -> new StatusException("Status for Candidate Id: " + id + " not found"));

        Status updatedStatus = statusMapper.toStatus(statusRequest);
        updatedStatus.setId(currentStatus.getId());

        StatusType currentStatusType = currentStatus.getStatusType();
        StatusType updatedStatusType = updatedStatus.getStatusType();

        boolean canTransition = currentStatusType.canTransitionTo(updatedStatusType);
        if (!canTransition) {
            throw new StatusException("Can't change status type: " + currentStatusType + " to " + updatedStatusType);
        }

        updatedStatus = statusRepository.save(updatedStatus);

        if (updatedStatus.getStatusType().equals(StatusType.OFFERED)) {
            String jwtToken = extractJwtFromRequest(request);
            notificationProducerService.sendMessageToNotificationMS(
                    new NotificationMessage(
                            "hiringNotificationTopicExchange",
                            "notification.offered",
                            updatedStatus.getId(),
                            updatedStatus.getCandidateId(),
                            updatedStatus.getStatusType(),
                            jwtToken
                    )
            );
        }

        return statusMapper.toResponse(updatedStatus);
    }

    public void delete(final UUID id) {
        if (!statusRepository.existsByCandidateId(id)) {
            throw new StatusException("Status for Candidate Id: " + id + " not found");
        }
        statusRepository.deleteByCandidateId(id);
    }



    public String extractJwtFromRequest(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }
}
