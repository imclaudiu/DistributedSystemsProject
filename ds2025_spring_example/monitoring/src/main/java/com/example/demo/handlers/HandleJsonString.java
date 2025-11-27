package com.example.demo.handlers;

import com.example.demo.dtos.DataReceive;
import com.example.demo.entities.Device;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Component
public class HandleJsonString {

    public DataReceive toDataReceive(String jsonString) throws JsonProcessingException {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());

        // Parse string into a JSON tree
            JsonNode node = mapper.readTree(jsonString);

            // Or convert to your DataReceive class
            DataReceive data = mapper.treeToValue(node, DataReceive.class);
            return data;
    }

    public Device toDevice(String jsonString) throws JsonProcessingException {
        ObjectMapper mapper  = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        JsonNode node = mapper.readTree(jsonString);

        Device device = mapper.treeToValue(node, Device.class);
        return device;
    }

}
