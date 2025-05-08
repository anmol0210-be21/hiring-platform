package com.hiring.candidatems.service;

import com.hiring.candidatems.domain.dto.BankInfoRequest;
import com.hiring.candidatems.domain.dto.BankInfoResponse;
import com.hiring.candidatems.domain.entity.BankInfo;
import com.hiring.candidatems.domain.entity.Candidate;
import com.hiring.candidatems.domain.mapper.BankInfoMapper;
import com.hiring.candidatems.exception.BankInfoException;
import com.hiring.candidatems.repository.BankInfoRepository;
import com.hiring.candidatems.repository.CandidateRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BankInfoService {
    private final BankInfoRepository bankInfoRepository;
    private final CandidateRepository candidateRepository;
    private final BankInfoMapper bankInfoMapper;

    public BankInfoService(final BankInfoRepository bankInfoRepository,
                           final CandidateRepository candidateRepository,
                           final BankInfoMapper bankInfoMapper) {
        this.bankInfoRepository = bankInfoRepository;
        this.candidateRepository = candidateRepository;
        this.bankInfoMapper = bankInfoMapper;
    }

    public BankInfoResponse getBankInfo(final UUID id) {
        return bankInfoMapper.toBankInfoResponse(
                bankInfoRepository.findByCandidateId(id)
                        .orElseThrow(
                                () -> new BankInfoException(
                                        "Bank info with Candidate id " + id + " not found"
                                )
                        )
        );
    }

    public BankInfoResponse addBankInfo(final BankInfoRequest bankInfoRequest) {
        BankInfo bankInfo = bankInfoMapper.toBankInfo(bankInfoRequest);

        Candidate candidate = candidateRepository.
                findById(bankInfoRequest.candidateId())
                .orElseThrow(
                        () -> new BankInfoException("Candidate with id " + bankInfoRequest.candidateId() + " not found")
                );

        bankInfo.setCandidate(candidate);

        return bankInfoMapper.toBankInfoResponse(
                bankInfoRepository.save(bankInfo)
        );
    }

    public BankInfoResponse updateBankInfo(final BankInfoRequest bankInfoRequest, final UUID id) {
        BankInfo bankInfo = bankInfoMapper.toBankInfo(bankInfoRequest);

        BankInfo currentBankInfo = bankInfoRepository.findByCandidateId(id)
                        .orElseThrow(() -> new BankInfoException("Bank info with Candidate id " + id + " not found"));
        bankInfo.setId(currentBankInfo.getId());

        return bankInfoMapper.toBankInfoResponse(
                bankInfoRepository.save(bankInfo)
        );
    }

    public void deleteBankInfo(final UUID id) {
        if (!bankInfoRepository.existsByCandidateId(id)) {
            throw new BankInfoException("Bank info with Candidate id " + id + " not found");
        }
        bankInfoRepository.deleteByCandidateId(id);
    }
}
