package com.hiring.candidatems.controller;

import com.hiring.candidatems.domain.dto.CandidateRequest;
import com.hiring.candidatems.domain.dto.CandidateResponse;
import com.hiring.candidatems.service.CandidateService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/candidates")
public class CandidateController {
    private final CandidateService candidateService;

    public CandidateController(final CandidateService candidateService) {
        this.candidateService = candidateService;
    }


    @GetMapping("/all")
    public ResponseEntity<List<CandidateResponse>> getAllCandidates() {
        return new ResponseEntity<>(candidateService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CandidateResponse> getCandidateById(@PathVariable @Valid final UUID id) {
        return new ResponseEntity<>(candidateService.findById(id), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<CandidateResponse> addCandidate(@RequestBody @Valid final CandidateRequest candidateRequest) {
        return new ResponseEntity<>(candidateService.add(candidateRequest), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CandidateResponse> updateCandidate(@PathVariable final @Valid UUID id, final @RequestBody @Valid CandidateRequest candidateRequest) {
        return new ResponseEntity<>(candidateService.update(candidateRequest, id), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CandidateResponse> deleteCandidate(@PathVariable @Valid final UUID id) {
        candidateService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<String> getCandidateCount() {
        return new ResponseEntity<>(candidateService.count(), HttpStatus.OK);
    }
}
