package com.example.demo.services;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    TokenService tokenService;

    public KafkaConsumerService(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @KafkaListener(topics = "my_topic", groupId = "group_id")
    public void consume(String message) {

        System.out.println("Message received: " + tokenService.extractUserId(message));
    }
}