package com.hiring.notificationms.client;

import com.hiring.notificationms.domain.dto.CandidateResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;
import java.util.UUID;

@FeignClient(
        name = "candidate",
        url = "http://localhost:8222/api/candidates/"
)
public interface CandidateClient {
    @GetMapping("/{id}")
    Optional<CandidateResponse> getCandidateById(@PathVariable("id") UUID id);
}
