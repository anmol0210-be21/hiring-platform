package com.hiring.notificationms.service;

import com.hiring.notificationms.domain.dto.JobOfferEmail;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.nio.charset.StandardCharsets;

@Service
public class MailerService {
    private static final Logger log = LoggerFactory.getLogger(MailerService.class);
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    public MailerService(final JavaMailSender mailSender,
                         final TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
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
            log.info("Mail sent successfully");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
