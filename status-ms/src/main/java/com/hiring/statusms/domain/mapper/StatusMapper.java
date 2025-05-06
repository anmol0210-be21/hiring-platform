package com.hiring.statusms.domain.mapper;

import com.hiring.statusms.domain.dto.StatusRequest;
import com.hiring.statusms.domain.dto.StatusResponse;
import com.hiring.statusms.domain.entity.Status;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StatusMapper {
    StatusResponse toResponse(Status status);
    Status toStatus(StatusRequest statusRequest);
}
