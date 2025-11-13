package com.example.demo.services;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class KafkaProducerService {

    private static final String TOPIC = "my_topic";
    private final KafkaTemplate<String, String> kafkaTemplate;

    AuthenticationService authenticationService;

    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate, AuthenticationService authenticationService) {
        this.kafkaTemplate = kafkaTemplate;
        this.authenticationService = authenticationService;
    }

    public void sendMessage(String token) {
        kafkaTemplate.send(TOPIC, token);
        System.out.println("Message sent: " + token);
    }
}