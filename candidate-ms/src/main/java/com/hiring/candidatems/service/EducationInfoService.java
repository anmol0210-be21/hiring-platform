package com.hiring.candidatems.service;

import com.hiring.candidatems.domain.dto.EducationInfoRequest;
import com.hiring.candidatems.domain.dto.EducationInfoResponse;
import com.hiring.candidatems.domain.entity.Candidate;
import com.hiring.candidatems.domain.entity.EducationInfo;
import com.hiring.candidatems.domain.mapper.EducationInfoMapper;
import com.hiring.candidatems.exception.EducationInfoException;
import com.hiring.candidatems.repository.CandidateRepository;
import com.hiring.candidatems.repository.EducationInfoRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EducationInfoService {
    private final EducationInfoRepository educationInfoRepository;
    private final CandidateRepository candidateRepository;
    private final EducationInfoMapper educationInfoMapper;

    public EducationInfoService(EducationInfoRepository educationInfoRepository,
                                CandidateRepository candidateRepository,
                                EducationInfoMapper educationInfoMapper) {
        this.educationInfoRepository = educationInfoRepository;
        this.candidateRepository = candidateRepository;
        this.educationInfoMapper = educationInfoMapper;
    }

    public EducationInfoResponse getEducationInfo(final UUID id) {
        return educationInfoMapper.toEducationInfoResponse(
                educationInfoRepository.findByCandidateId(id)
                        .orElseThrow(
                                () -> new EducationInfoException(
                                        "Education info with Candidate id " + id + " not found"
                                )
                        )
        );
    }

    public EducationInfoResponse addEducationInfo(final EducationInfoRequest educationInfoRequest) {
        EducationInfo educationInfo = educationInfoMapper.toEducationInfo(educationInfoRequest);

        Candidate candidate = candidateRepository.
                findById(educationInfoRequest.candidateId())
                .orElseThrow(
                        () -> new EducationInfoException("Candidate with id " + educationInfoRequest.candidateId() + " not found")
                );

        educationInfo.setCandidate(candidate);

        return educationInfoMapper.toEducationInfoResponse(
                educationInfoRepository.save(educationInfo)
        );
    }

    public EducationInfoResponse updateEducationInfo(final EducationInfoRequest educationInfoRequest, final UUID id) {
        EducationInfo educationInfo = educationInfoMapper.toEducationInfo(educationInfoRequest);

        EducationInfo currentEducationInfo = educationInfoRepository.findByCandidateId(id)
                        .orElseThrow(() -> new EducationInfoException("Education info with Candidate id " + id + " not found"));

        educationInfo.setId(currentEducationInfo.getId());

        return educationInfoMapper.toEducationInfoResponse(
                educationInfoRepository.save(educationInfo)
        );
    }

    public void deleteEducationInfo(final UUID id) {
        if (!educationInfoRepository.existsByCandidateId(id)) {
            throw new EducationInfoException("Education info with Candidate id " + id + " not found");
        }
        educationInfoRepository.deleteByCandidateId(id);
    }
}
