package com.example.demo.kafka;


import com.example.demo.dtos.DeviceDetailsDTO;
import com.example.demo.entities.Device;
import com.example.demo.services.DeviceService;
import com.example.demo.services.PersonService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class UserConsumer {

    private final PersonService personService;
    private final DeviceService deviceService;

    public UserConsumer(PersonService personService, DeviceService deviceService) {
        this.personService = personService;
        this.deviceService = deviceService;
    }


    @KafkaListener(topics = "add-user-in-device", groupId = "device-group")
    public void addUser(String message){
        try {
            String newMessage = message.replace("\"", "");
            UUID id = UUID.fromString(newMessage);
            System.out.println("Message received: " + id);
            this.personService.addPerson(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @KafkaListener(topics = "delete-user-in-device", groupId = "device-group")
    public void deleteUser(String message){
        try {
            String newMessage = message.replace("\"", "");
            UUID id = UUID.fromString(newMessage);
            System.out.println("Message received: " + id);
            this.personService.deletePerson(id);
            List<DeviceDetailsDTO> deviceList = deviceService.findByOwnerId(id);
            for(DeviceDetailsDTO device : deviceList){
                deviceService.deleteDevice(device.getId());
            }
//            this.deviceService.deleteDevicesWithOwnerId(id);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
