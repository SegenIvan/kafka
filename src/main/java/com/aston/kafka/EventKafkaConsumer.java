package com.aston.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventKafkaConsumer {
    private final NotificationService emailService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "${app.kafka.topic.user-events}")
    public void handleUserEvent(ConsumerRecord<String, String> record) {
        try {
            MyEvent event = objectMapper.readValue(record.value(), MyEvent.class);
            if ("USER_CREATED".equals(event.eventType())) {
                emailService.sendAccountCreatedEmail(event.email());
            } else if ("USER_DELETED".equals(event.eventType())) {
                emailService.sendAccountDeletedEmail(event.email());
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize UserEvent", e);
        }
    }
}
