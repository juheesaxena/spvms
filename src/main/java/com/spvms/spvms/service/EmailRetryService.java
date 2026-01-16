package com.spvms.spvms.service;

import com.spvms.spvms.models.EmailLog;
import com.spvms.spvms.repository.EmailLogRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailRetryService {

    private final EmailLogRepository emailLogRepository;
    private final JavaMailSender mailSender;

    public EmailRetryService(EmailLogRepository emailLogRepository,
                             JavaMailSender mailSender) {
        this.emailLogRepository = emailLogRepository;
        this.mailSender = mailSender;
    }

    @Scheduled(fixedRate = 300000) // every 5 minutes
    public void retryFailedEmails() {

        List<EmailLog> failedEmails =
                emailLogRepository.findByStatus("FAILED");

        for (EmailLog log : failedEmails) {
            try {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(log.getToEmail());
                message.setSubject(log.getSubject());
                message.setText(log.getBody());

                mailSender.send(message);

                log.setStatus("SENT");
                emailLogRepository.save(log);

            } catch (Exception e) {
                log.setRetryCount(log.getRetryCount() + 1);
                emailLogRepository.save(log);
            }
        }
    }
}
