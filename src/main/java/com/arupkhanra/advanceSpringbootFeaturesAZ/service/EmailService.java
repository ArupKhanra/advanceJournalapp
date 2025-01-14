package com.arupkhanra.advanceSpringbootFeaturesAZ.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendMail(String to, String subject, String body) {
        try {
            // Create a SimpleMailMessage
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setTo(to);
            mail.setSubject(subject);
            mail.setText(body);

            // Send the email
            javaMailSender.send(mail);

            // Log success
            log.info("Email sent successfully to {}", to);
        } catch (Exception e) {
            // Log the exception details with a clear message
            log.error("Exception occurred while sending email to {}: {}", to, e.getMessage(), e);
        }
    }
}