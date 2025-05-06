package com.hiring.candidatems.domain.mapper;

import com.hiring.candidatems.domain.dto.CandidateRequest;
import com.hiring.candidatems.domain.dto.CandidateResponse;
import com.hiring.candidatems.domain.entity.Candidate;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CandidateMapper {
    CandidateResponse toCandidateResponse(Candidate candidate);
    Candidate toCandidate(CandidateRequest candidateRequest);
}
