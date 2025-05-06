package com.hiring.notificationms.service;

import com.hiring.notificationms.client.CandidateClient;
import com.hiring.notificationms.domain.dto.CandidateResponse;
import com.hiring.notificationms.domain.dto.JobOfferEmail;
import com.hiring.notificationms.domain.dto.NotificationMessage;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    private final MailerService mailerService;
    private final CandidateClient candidateClient;

    public NotificationService(final MailerService mailerService,
                               final CandidateClient candidateClient) {
        this.mailerService = mailerService;
        this.candidateClient = candidateClient;
    }

    public void sendJobOffer(NotificationMessage notificationMessage) {
        CandidateResponse candidateResponse = candidateClient.getCandidateById(
                notificationMessage.candidateId()
        ).orElseThrow(
                () -> new RuntimeException("Candidate not found")
        );

        mailerService.sendJobOfferMail(
                new JobOfferEmail(
                        candidateResponse.email(),
                        "Job Offer - CIA",
                        candidateResponse.firstName() + " " + candidateResponse.lastName(),
                        "SDE",
                        "2025-12-01",
                        "2020200",
                        candidateResponse.city(),
                        "hehe",
                        "email@cia.gov",
                        "cia.gov",
                        "CIA"
                )
        );
    }
}
