package com.example.shoppinglist.service;

import com.example.shoppinglist.config.AppConfig;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailService {

    private JavaMailSender mailSender;
    private AppConfig appConfig;

    @Async
    public void send(String to, String emailText) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "utf-8");
            mimeMessageHelper.setText(emailText, true);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject("Confirm your email");
            mimeMessageHelper.setFrom(appConfig.getMyEmail());
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new IllegalStateException("Failed to send email.");
        }
    }
}
