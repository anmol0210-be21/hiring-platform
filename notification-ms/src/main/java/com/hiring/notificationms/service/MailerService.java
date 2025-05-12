package com.hiring.notificationms.service;

import com.hiring.notificationms.domain.dto.JobOfferEmail;
import com.hiring.notificationms.domain.dto.OtpMessage;
import com.hiring.notificationms.domain.entity.OfferNotification;
import com.hiring.notificationms.repository.OfferNotificationRepository;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Service
public class MailerService {
    private static final Logger log = LoggerFactory.getLogger(MailerService.class);
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;
    private final OfferNotificationRepository offerNotificationRepository;

    public MailerService(final JavaMailSender mailSender,
                         final TemplateEngine templateEngine,
                         final OfferNotificationRepository offerNotificationRepository) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
        this.offerNotificationRepository = offerNotificationRepository;
    }


    public void sendOtpMail(final OtpMessage otpMessage) {
        try{
            Context context = new Context();
            context.setVariable("otp", otpMessage.otp());
            context.setVariable("email", otpMessage.email());

            String htmlContent = templateEngine.process("otp-email-template", context);

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(
                    mimeMessage,
                    true,
                    StandardCharsets.UTF_8.name()
            );
            helper.setTo(otpMessage.email());
            helper.setSubject("OTP Notification");
            helper.setText(htmlContent, true);

            mailSender.send(mimeMessage);
        }
        catch (Exception e){
            log.error(e.toString());
        }
    }

    public void sendJobOfferMail(JobOfferEmail jobOfferEmail) {
        try{
            Context context = new Context();
            context.setVariable("email", jobOfferEmail);

            String htmlContent = templateEngine.process("job-offer-template", context);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());

            helper.setTo(jobOfferEmail.to());
            helper.setSubject(jobOfferEmail.subject());
            helper.setText(htmlContent, true);

            mailSender.send(message);

            saveOfferNotification(jobOfferEmail.candidateId());
        } catch (Exception e) {
            log.error(e.toString());
        }
    }

    private void saveOfferNotification(UUID candidateId) {
        OfferNotification offerNotification = new OfferNotification();
        offerNotification.setCandidateId(candidateId);

        offerNotificationRepository.save(offerNotification);
    }
}
