package com.hiring.candidatems.domain.mapper;

import com.hiring.candidatems.domain.dto.EducationInfoRequest;
import com.hiring.candidatems.domain.dto.EducationInfoResponse;
import com.hiring.candidatems.domain.entity.EducationInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EducationInfoMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "candidateId", target = "candidate.id")
    EducationInfo toEducationInfo(EducationInfoRequest educationInfoRequest);

    @Mapping(source = "candidate.id", target = "candidateId")
    EducationInfoResponse toEducationInfoResponse(EducationInfo educationInfo);
}
