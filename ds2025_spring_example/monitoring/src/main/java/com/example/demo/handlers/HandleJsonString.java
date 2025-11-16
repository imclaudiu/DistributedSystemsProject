package com.example.demo.handlers;

import com.example.demo.entities.Data;
import com.example.demo.entities.DataReceive;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Component
public class HandleJsonString {

    public DataReceive toJsonString(String jsonString) throws JsonProcessingException {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());

        // Parse string into a JSON tree
            JsonNode node = mapper.readTree(jsonString);

            // Or convert to your DataReceive class
            DataReceive data = mapper.treeToValue(node, DataReceive.class);
            return data;
    }


}
