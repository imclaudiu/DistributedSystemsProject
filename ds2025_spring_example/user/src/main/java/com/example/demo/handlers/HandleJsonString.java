package com.example.demo.handlers;

import com.example.demo.entities.Person;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Component
public class HandleJsonString {

    public Person toPerson(String jsonString) throws JsonProcessingException {
        ObjectMapper mapper  = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        JsonNode node = mapper.readTree(jsonString);

        return mapper.treeToValue(node, Person.class);
    }

}
