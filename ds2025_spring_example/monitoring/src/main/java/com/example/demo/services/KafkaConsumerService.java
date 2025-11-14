package com.example.demo.services;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class KafkaConsumerService {

    private final TokenService tokenService;
    private final DataService dataService;

    public KafkaConsumerService(TokenService tokenService, DataService dataService) {
        this.tokenService = tokenService;
        this.dataService = dataService;
    }

    @KafkaListener(topics = "data-topic", groupId = "project-group")
    public void createData(String message) {
        this.dataService.insert(UUID.fromString(message));
        /*
        *  TO-DO
        * Create a device in device service and make it so that when it's created, through Kafka, creates a Data for it.
        * */
        System.out.println("Message received: " + message);
    }
}