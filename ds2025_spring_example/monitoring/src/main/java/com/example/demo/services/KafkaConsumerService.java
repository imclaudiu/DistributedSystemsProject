package com.example.demo.services;

import com.example.demo.entities.DataReceive;
import com.example.demo.handlers.HandleJsonString;
import com.example.demo.repositories.DataRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class KafkaConsumerService {

    private final TokenService tokenService;
    private final DataService dataService;
    private final HandleJsonString handleJsonString;
    private final DataRepository dataRepository;

    private final List<DataReceive> receivedData = new ArrayList<>();

    public KafkaConsumerService(TokenService tokenService, DataService dataService, HandleJsonString handleJsonString, DataRepository dataRepository) {
        this.tokenService = tokenService;
        this.dataService = dataService;
        this.handleJsonString = handleJsonString;
        this.dataRepository = dataRepository;
    }

    @KafkaListener(topics = "sim_data", groupId = "project-group")
    public void createData(String message) {
        try {
            DataReceive data = handleJsonString.toJsonString(message);

            if (data != null) {
                System.out.println("Message received: " + data);
                receivedData.add(data);

                // calcul consumuri pe ore întregi
                calculateHourlyConsumption();

            } else {
                System.out.println("Received invalid JSON: " + message);
            }

        } catch (Exception e) {
            System.out.println("Failed to parse message: " + message);
            e.printStackTrace();
        }
    }

    private void calculateHourlyConsumption() {
        if (receivedData.isEmpty()) return;

        // intervalul curent definit pe ore întregi
        LocalTime currentTime = LocalTime.now();
        int currentHour = currentTime.getHour();
        LocalTime intervalStart = LocalTime.of(currentHour, 0);
        LocalTime intervalEnd = LocalTime.of(currentHour, 59, 59);

        int totalConsumption = 0;
        UUID deviceId = null;

        for (DataReceive d : receivedData) {
            LocalTime t = d.getTime();
            if (!t.isBefore(intervalStart) && !t.isAfter(intervalEnd)) {
                totalConsumption += d.getMeasurementValue();
                deviceId = d.getDeviceId(); // presupunem că luăm consum per device
            }
        }


        if (deviceId != null) {
            if(!dataRepository.findByDeviceIdAndTime(deviceId, intervalStart).isPresent()){
                dataService.insertWithValues(deviceId, intervalStart, 0);
            }
            // salvăm în DB consumul pentru interval
            dataService.update(deviceId, intervalStart, totalConsumption);
        }

        System.out.println("=== Consum interval " + intervalStart + " - " + intervalEnd + " ===");
        System.out.println("Total: " + totalConsumption);
        System.out.println("----------------------------------------");
    }
}
