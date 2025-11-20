package com.example.simulator.services;

import com.example.simulator.entities.Data;
import org.springframework.cglib.core.Local;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class KafkaProducerService {

    private static final String TOPIC = "sim_data";
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final KafkaTemplate<String, Object> jsonKafkaTemplate;
//    private final ScheduledExecutorService

    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate, KafkaTemplate<String, Object> jsonKafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.jsonKafkaTemplate = jsonKafkaTemplate;
    }

    @Scheduled(fixedRate = 60000)
    public void sendMessage() {
        Data data = new Data(UUID.fromString("5e747761-12cc-4345-a260-b685e0e1dc7a"), LocalTime.now(), ThreadLocalRandom.current().nextInt(0,20));
//        Object object = data;
        jsonKafkaTemplate.send(TOPIC, data);
        System.out.println("Message sent: " + data);
    }
}