package com.hiring.notificationms.service;

import com.hiring.notificationms.client.CandidateClient;
import com.hiring.notificationms.domain.dto.OfferNotificationResponse;
import com.hiring.notificationms.domain.entity.OfferNotification;
import com.hiring.notificationms.domain.dto.CandidateResponse;
import com.hiring.notificationms.domain.dto.JobOfferEmail;
import com.hiring.notificationms.domain.dto.NotificationMessage;
import com.hiring.notificationms.repository.OfferNotificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OfferNotificationService {
    private final MailerService mailerService;
    private final CandidateClient candidateClient;
    private final OfferNotificationRepository offerNotificationRepository;

    public OfferNotificationService(final MailerService mailerService,
                                    final CandidateClient candidateClient,
                                    final OfferNotificationRepository offerNotificationRepository) {
        this.mailerService = mailerService;
        this.candidateClient = candidateClient;
        this.offerNotificationRepository = offerNotificationRepository;
    }

    public void sendJobOffer(NotificationMessage notificationMessage) {
        CandidateResponse candidateResponse = candidateClient.getCandidateById(
                notificationMessage.candidateId()
        ).orElseThrow(
                () -> new RuntimeException("Candidate not found")
        );

        mailerService.sendJobOfferMail(
                new JobOfferEmail(
                        candidateResponse.id(),
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

    public List<OfferNotificationResponse> getAllOfferNotifications() {
        List<OfferNotification> offerNotifications = offerNotificationRepository.findAll();

        return offerNotifications.stream()
                .map(this::maptoOfferNotificationResponse).toList();
    }

    public List<OfferNotificationResponse> getOfferNotificationsByCandidateId(final UUID candidateId) {
        List<OfferNotification> offerNotifications = offerNotificationRepository
                .findAllByCandidateId(candidateId)
                .orElseThrow(() -> new RuntimeException("Candidate not found"));

        return offerNotifications.stream()
                .map(this::maptoOfferNotificationResponse).toList();
    }

    private OfferNotificationResponse maptoOfferNotificationResponse(final OfferNotification offerNotification) {
        return new OfferNotificationResponse(
                offerNotification.getId(),
                offerNotification.getCandidateId(),
                offerNotification.getSentAt(),
                offerNotification.getCreatedDate(),
                offerNotification.getLastModifiedDate()
        );
    }
}
