package com.hiring.notificationms.domain.dto;

public record JobOfferEmail(
         String to,
         String subject,
         String candidateName,
         String position,
         String startDate,
         String salary,
         String location,
         String benefits,
         String companyEmail,
         String companyWebsite,
         String companyName
) {
}
