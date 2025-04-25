package com.school.management.kafka;


import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService {

    @KafkaListener(topics = "user-events", groupId = "group_id")
    public void consume(String message) {
        System.out.println("Kafka message consumed: " + message);
    }
}
