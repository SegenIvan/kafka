package com.aston.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final JavaMailSender mailSender;
    private final Environment env;

    public void sendAccountCreatedEmail(String email) {
        String subject = "Добро пожаловать!";
        String text = "Здравствуйте! Ваш аккаунт на сайте " +
                env.getProperty("app.site-name", "наш сайт") + " был успешно создан.";

        sendEmail(email, subject, text);
    }

    public void sendAccountDeletedEmail(String email) {
        String subject = "Ваш аккаунт был удалён";
        String text = "Здравствуйте! Ваш аккаунт был удалён.";

        sendEmail(email, subject, text);
    }

    private void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(env.getProperty("spring.mail.username"));
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }
}
