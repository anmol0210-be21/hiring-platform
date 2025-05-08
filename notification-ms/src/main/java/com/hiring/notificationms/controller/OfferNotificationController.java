package com.hiring.notificationms.controller;

import com.hiring.notificationms.domain.dto.OfferNotificationResponse;
import com.hiring.notificationms.service.OfferNotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/notifications")
public class OfferNotificationController {
    private final OfferNotificationService offerNotificationService;

    public OfferNotificationController(final OfferNotificationService offerNotificationService) {
        this.offerNotificationService = offerNotificationService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<OfferNotificationResponse>> getAllNotifications() {
        return new ResponseEntity<>(offerNotificationService.getAllOfferNotifications(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<OfferNotificationResponse>> getNotificationById(@PathVariable final UUID id) {
        return new ResponseEntity<>(offerNotificationService.getOfferNotificationsByCandidateId(id), HttpStatus.OK);
    }
}
