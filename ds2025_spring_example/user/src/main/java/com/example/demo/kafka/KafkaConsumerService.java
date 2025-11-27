package com.example.demo.kafka;

import com.example.demo.entities.Person;
import com.example.demo.handlers.HandleJsonString;
import com.example.demo.services.PersonService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.UUID;

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

    @KafkaListener(topics = "delete-auth", groupId = "group_id")
    public void delete(String message) {
        try {
            System.out.println("Message received: " + message);
            String newMessage = message.replace("\"", "");
            UUID id = UUID.fromString(newMessage);
            this.personService.delete(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}