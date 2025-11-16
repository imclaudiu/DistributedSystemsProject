package com.example.demo.services;

import com.example.demo.entities.DataReceive;
import com.example.demo.handlers.HandleJsonString;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class KafkaConsumerService {

    private final TokenService tokenService;
    private final DataService dataService;
    private final HandleJsonString handleJsonString;

    public KafkaConsumerService(TokenService tokenService, DataService dataService, HandleJsonString handleJsonString) {
        this.tokenService = tokenService;
        this.dataService = dataService;
        this.handleJsonString = handleJsonString;
    }

    @KafkaListener(topics = "sim_data", groupId = "project-group")
    public void createData(String message) {
        try {
            // Parse JSON safely
            DataReceive data = handleJsonString.toJsonString(message);

            if (data != null) {
                System.out.println("Message received: " + data);
                // Optionally, insert into your service
                // dataService.insert(data);
            } else {
                System.out.println("Received invalid JSON: " + message);
            }

        } catch (Exception e) {
            System.out.println("Failed to parse message: " + message);
            e.printStackTrace();
            // Do NOT throw exception to avoid listener crash
        }
    }

}