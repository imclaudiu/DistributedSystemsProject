package com.example.demo.services;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    TokenService tokenService;

    public KafkaConsumerService(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @KafkaListener(topics = "register-topic1", groupId = "group_id")
    public void consume(String message) {

        System.out.println("Message received: " + message);
    }
}