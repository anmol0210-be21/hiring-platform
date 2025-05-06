package com.hiring.statusms.service;

import com.hiring.statusms.domain.dto.StatusRequest;
import com.hiring.statusms.domain.dto.StatusResponse;
import com.hiring.statusms.domain.entity.Status;
import com.hiring.statusms.domain.enums.StatusType;
import com.hiring.statusms.domain.mapper.StatusMapper;
import com.hiring.statusms.exception.StatusException;
import com.hiring.statusms.repository.StatusRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class StatusService {
    private final StatusRepository statusRepository;
    private final StatusMapper statusMapper;

    public StatusService(final StatusRepository statusRepository,
                         final StatusMapper statusMapper) {
        this.statusRepository = statusRepository;
        this.statusMapper = statusMapper;
    }

    public List<StatusResponse> getAll() {
        return statusRepository.findAll().stream()
                .map(statusMapper::toResponse)
                .toList();
    }

    public StatusResponse getById(final UUID id) {
        return statusMapper.toResponse(
                statusRepository.findById(id)
                        .orElseThrow(() -> new StatusException("Status by Id: " + id + " not found"))
        );
    }

    public StatusResponse add(final StatusRequest statusRequest) {
        Status status = statusMapper.toStatus(statusRequest);
        Status savedStatus = statusRepository.save(status);
        return statusMapper.toResponse(savedStatus);
    }

    public StatusResponse update(final StatusRequest statusRequest, final UUID id) {
        Status currentStatus = statusRepository.findById(id)
                .orElseThrow(() -> new StatusException("Status by Id: " + id + " not found"));

        Status updatedStatus = statusMapper.toStatus(statusRequest);
        updatedStatus.setId(id);

        StatusType currentStatusType = currentStatus.getStatusType();
        StatusType updatedStatusType = updatedStatus.getStatusType();

        boolean canTransition = currentStatusType.canTransitionTo(updatedStatusType);
        if (!canTransition) {
            throw new StatusException("Can't change status type: " + currentStatusType + " to " + updatedStatusType);
        }

        updatedStatus = statusRepository.save(updatedStatus);
        return statusMapper.toResponse(updatedStatus);
    }

    public void delete(final UUID id) {
        if (!statusRepository.existsById(id)) {
            throw new StatusException("Status by Id: " + id + " not found");
        }
        statusRepository.deleteById(id);
    }
}
