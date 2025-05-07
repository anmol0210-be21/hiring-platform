package com.hiring.candidatems.domain.mapper;

import com.hiring.candidatems.domain.dto.BankInfoRequest;
import com.hiring.candidatems.domain.dto.BankInfoResponse;
import com.hiring.candidatems.domain.entity.BankInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BankInfoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "candidateId", target = "candidate.id")
    BankInfo toBankInfo(BankInfoRequest bankInfoRequest);

    @Mapping(source = "candidate.id", target = "candidateId")
    BankInfoResponse toBankInfoResponse(BankInfo bankInfo);
}
