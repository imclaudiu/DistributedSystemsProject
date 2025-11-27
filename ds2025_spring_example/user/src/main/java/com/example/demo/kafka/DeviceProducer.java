package com.example.demo.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DeviceProducer {
    private static final String addDeviceTopic = "add-user-in-device";
    private static final String deleteDeviceTopic = "delete-user-in-device";

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public DeviceProducer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void setAddUserTopic(UUID id){
        try {
            String message = objectMapper.writeValueAsString(id);
            kafkaTemplate.send(addDeviceTopic, message);
        } catch (JacksonException e) {
            throw new RuntimeException("Kafka user producer failure!", e);
        }
    }

    public void setDeleteUserTopic(UUID id){
        try {
            String message = objectMapper.writeValueAsString(id);
            kafkaTemplate.send(deleteDeviceTopic, message);
        } catch (JacksonException e) {
            throw new RuntimeException("Kafka user producer failure!", e);
        }
    }
}
