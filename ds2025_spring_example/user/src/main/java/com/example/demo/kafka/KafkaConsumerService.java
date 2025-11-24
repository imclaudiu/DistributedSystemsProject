package com.example.demo.kafka;

import com.example.demo.entities.Person;
import com.example.demo.handlers.HandleJsonString;
import com.example.demo.services.PersonService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class KafkaConsumerService {

    private final PersonService personService;

    public KafkaConsumerService(PersonService personService) {
        this.personService = personService;
    }

    @KafkaListener(topics = "register-topic", groupId = "group_id")
    public void consume(String message) {

        try {
            Person person = new HandleJsonString().toPerson(message);
            this.personService.insert(person);
            System.out.println("Message received: " + message);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Receiving person error: ", e);
        }
    }
}