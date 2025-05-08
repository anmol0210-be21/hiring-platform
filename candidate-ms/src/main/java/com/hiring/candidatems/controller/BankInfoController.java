package com.hiring.candidatems.controller;

import com.hiring.candidatems.domain.dto.BankInfoRequest;
import com.hiring.candidatems.domain.dto.BankInfoResponse;
import com.hiring.candidatems.service.BankInfoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/candidates/bankInfo")
public class BankInfoController {
    private final BankInfoService bankInfoService;

    public BankInfoController(final BankInfoService bankInfoService) {
        this.bankInfoService = bankInfoService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<BankInfoResponse> getBankInfo(@Valid @PathVariable final UUID id) {
        return new ResponseEntity<>(bankInfoService.getBankInfo(id), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<BankInfoResponse> addBankInfo(@Valid @RequestBody final BankInfoRequest request) {
        return new ResponseEntity<>(bankInfoService.addBankInfo(request), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<BankInfoResponse> updateBankInfo(@Valid @RequestBody final BankInfoRequest request,
                                                           @Valid @PathVariable final UUID id) {
        return new ResponseEntity<>(bankInfoService.updateBankInfo(request, id), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteBankInfo(@Valid @PathVariable final UUID id) {
        bankInfoService.deleteBankInfo(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
