package com.spvms.spvms.service;

import com.spvms.spvms.models.EmailLog;
import com.spvms.spvms.repository.EmailLogRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NotificationService {

    private final JavaMailSender mailSender;
    private final EmailLogRepository emailLogRepository;
    private final EmailTemplateBuilder templateBuilder;

    public NotificationService(JavaMailSender mailSender,
                               EmailLogRepository emailLogRepository,
                               EmailTemplateBuilder templateBuilder) {
        this.mailSender = mailSender;
        this.emailLogRepository = emailLogRepository;
        this.templateBuilder = templateBuilder;
    }

    public void sendPrSubmittedEmail(String toEmail, String prNumber, String requesterName) {
        String subject = "PR Submitted - " + prNumber;
        String body = templateBuilder.prSubmittedTemplate(prNumber, requesterName);
        sendEmail(toEmail, subject, body);
    }

    public void sendPrApprovedEmail(String toEmail, String prNumber) {
        String subject = "PR Approved - " + prNumber;
        String body = templateBuilder.prApprovedTemplate(prNumber);
        sendEmail(toEmail, subject, body);
    }

    private void sendEmail(String toEmail, String subject, String body) {

        EmailLog log = new EmailLog();
        log.setToEmail(toEmail);
        log.setSubject(subject);
        log.setBody(body);
        log.setRetryCount(0);

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject(subject);
            message.setText(body);

            mailSender.send(message);

            log.setStatus("SENT");
            log.setSentAt(LocalDateTime.now());

        } catch (Exception e) {
            log.setStatus("FAILED");
            log.setRetryCount(1);
            e.printStackTrace();
        }

        emailLogRepository.save(log);
    }
}
