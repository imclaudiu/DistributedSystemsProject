package com.example.demo.kafka;

import com.example.demo.dtos.PersonDTO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

import java.util.UUID;

@Component
public class KafkaProducerService {

    private static final String TOPIC = "register-topic";
    private static final String DELETE_TOPIC = "delete-auth";
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;


    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendMessage(PersonDTO personDTO) {
        try {
            String message = objectMapper.writeValueAsString(personDTO);
            kafkaTemplate.send(TOPIC, message);
            System.out.println("sent: " + message);
        } catch (JacksonException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteAuthMessage(UUID id){
        try {
            String message = objectMapper.writeValueAsString(id);
            kafkaTemplate.send(DELETE_TOPIC, message);
        } catch (JacksonException e) {
            throw new RuntimeException(e);
        }
    }
}