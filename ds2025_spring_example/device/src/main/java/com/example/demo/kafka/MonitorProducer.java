package com.example.demo.kafka;

import com.example.demo.dtos.DeviceMonitor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

import java.util.UUID;

@Component
public class MonitorProducer {

    private static final String addDeviceTopic = "add-device";
    private static final String deleteDeviceTopic = "delete-device";
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public MonitorProducer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void setAddDeviceTopic(DeviceMonitor deviceMonitor) {
        try {
            String message = objectMapper.writeValueAsString(deviceMonitor);
            kafkaTemplate.send(addDeviceTopic, message);
        } catch (JacksonException e) {
            throw new RuntimeException("Kafka device producer failure!", e);
        }
    }

    public void setDeleteDeviceTopic(UUID id){
        try {
            String message = objectMapper.writeValueAsString(id);
            kafkaTemplate.send(deleteDeviceTopic, message);
        }
        catch( JacksonException e){
            throw new RuntimeException("Kafka device producer failure!", e);
        }
    }
}