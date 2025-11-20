package com.example.demo.kafka;

import com.example.demo.entities.Device;
import com.example.demo.handlers.HandleJsonString;
import com.example.demo.services.DeviceService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class DeviceListener {
    private final HandleJsonString handleJsonString;
    private final DeviceService deviceService;

    public DeviceListener(HandleJsonString handleJsonString, DeviceService deviceService) {
        this.handleJsonString = handleJsonString;
        this.deviceService = deviceService;
    }

    @KafkaListener(topics = "add-device", groupId = "project-group")
    public void addDevice(String message){
        try {
            Device device = handleJsonString.toDevice(message);
            if(device != null)
            {
                deviceService.insert(device);
            }
        } catch (JsonProcessingException e) {
            System.out.println("Failed " + message);
            e.printStackTrace();
        }
    }

}
