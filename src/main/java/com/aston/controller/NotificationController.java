package com.aston.controller;

import com.aston.kafka.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @PostMapping("/created")
    public ResponseEntity<Void> sendAccountCreatedNotification(@RequestParam String email) {
        notificationService.sendAccountCreatedEmail(email);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/deleted")
    public ResponseEntity<Void> sendAccountDeletedNotification(@RequestParam String email) {
        notificationService.sendAccountDeletedEmail(email);
        return ResponseEntity.ok().build();
    }
}
