package com.example.demo.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.util.UUID;

@Component
public class WebSocketProducer {

    private static final String addDeviceTopic = "add-device";
    private static final String overLimitTopic = "over-limit";
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public WebSocketProducer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }


    public void overLimit(Integer consume) {
        try {
            String payload = objectMapper.writeValueAsString(consume);
            kafkaTemplate.send(overLimitTopic, payload);
        } catch (JacksonException e) {
            throw new RuntimeException("Kafka device producer failure!", e);
        }
    }
}