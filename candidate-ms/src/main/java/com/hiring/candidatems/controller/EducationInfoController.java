package com.hiring.candidatems.controller;

import com.hiring.candidatems.domain.dto.EducationInfoRequest;
import com.hiring.candidatems.domain.dto.EducationInfoResponse;
import com.hiring.candidatems.service.EducationInfoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/candidates/educationInfo")
public class EducationInfoController {
    private final EducationInfoService educationInfoService;

    public EducationInfoController(EducationInfoService educationInfoService) {
        this.educationInfoService = educationInfoService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<EducationInfoResponse> getEducationInfo(@PathVariable final UUID id) {
        return new ResponseEntity<>(educationInfoService.getEducationInfo(id), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<EducationInfoResponse> addEducationInfo(@RequestBody final EducationInfoRequest request) {
        return new ResponseEntity<>(educationInfoService.addEducationInfo(request), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<EducationInfoResponse> updateEducationInfo(@RequestBody final EducationInfoRequest request,
                                                           @PathVariable final UUID id) {
        return new ResponseEntity<>(educationInfoService.updateEducationInfo(request, id), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteEducationInfo(@PathVariable final UUID id) {
        educationInfoService.deleteEducationInfo(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
