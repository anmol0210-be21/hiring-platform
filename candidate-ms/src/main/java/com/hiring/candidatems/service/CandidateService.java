package com.hiring.candidatems.service;

import com.hiring.candidatems.domain.dto.CandidateRequest;
import com.hiring.candidatems.domain.dto.CandidateResponse;
import com.hiring.candidatems.domain.entity.Candidate;
import com.hiring.candidatems.domain.mapper.CandidateMapper;
import com.hiring.candidatems.exception.CandidateException;
import com.hiring.candidatems.repository.CandidateRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


// TODO: implement status microservice calls to init status

@Service
public class CandidateService {
    private final CandidateRepository candidateRepository;
    private final CandidateMapper candidateMapper;

    public CandidateService(final CandidateRepository candidateRepository,
                            final CandidateMapper candidateMapper) {
        this.candidateRepository = candidateRepository;
        this.candidateMapper = candidateMapper;
    }

    public List<CandidateResponse> findAll() {
        return candidateRepository.findAll().stream()
                .map(candidateMapper::toCandidateResponse)
                .toList();
    }

    public CandidateResponse findById(final UUID id) {
        return candidateMapper.toCandidateResponse(
                candidateRepository.findById(id).orElseThrow(
                        () -> new CandidateException("Could not find candidate with id: " + id)
                )
        );
    }

    public CandidateResponse add(final CandidateRequest request) {
        Candidate candidate = candidateMapper.toCandidate(request);
        candidate = candidateRepository.save(candidate);
        return candidateMapper.toCandidateResponse(candidate);
    }

    public CandidateResponse update(final CandidateRequest request, final UUID id) {
        Candidate candidate = candidateMapper.toCandidate(request);
        candidate.setId(id);
        candidate = candidateRepository.save(candidate);
        return candidateMapper.toCandidateResponse(candidate);
    }

    public void delete(final UUID id) {
        if (!candidateRepository.existsById(id)) {
            throw new CandidateException("Could not find candidate with id: " + id);
        }
        candidateRepository.deleteById(id);
    }
}
