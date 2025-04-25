package com.school.management.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProducerService {

    private final List<String> publishedMessages = new ArrayList<>();


    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendUserEvent(String message) {
        kafkaTemplate.send("user-events", message);
        publishedMessages.add(message);
    }

    public List<String> getPublishedMessages() {
        return publishedMessages;
    }
}