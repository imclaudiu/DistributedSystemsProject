package com.example.demo.services;

import com.example.demo.entities.DataReceive;
import com.example.demo.handlers.HandleJsonString;
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

    private final List<DataReceive> receivedData = new ArrayList<>();

    public KafkaConsumerService(TokenService tokenService, DataService dataService, HandleJsonString handleJsonString) {
        this.tokenService = tokenService;
        this.dataService = dataService;
        this.handleJsonString = handleJsonString;
    }

    @KafkaListener(topics = "sim_data", groupId = "project-group")
    public void createData(String message) {
        try {
            DataReceive data = handleJsonString.toJsonString(message);

            if (data != null) {
                System.out.println("Message received: " + data);
                receivedData.add(data);

                // calcul consumuri pe intervale de 10 minute la fiecare minut
                calculateConsumptionPerMinute();

                // Optionally, insert into your service
                // dataService.insert(data);
            } else {
                System.out.println("Received invalid JSON: " + message);
            }

        } catch (Exception e) {
            System.out.println("Failed to parse message: " + message);
            e.printStackTrace();
        }
    }

    private void calculateConsumptionPerMinute() {
        if (receivedData.isEmpty()) return;

        LocalTime currentTime = LocalTime.now();
        LocalTime startTime = currentTime.minusMinutes(1); // interval de 10 minute înainte de momentul curent
        int consumption = 0;
        UUID id = null;

        // sumăm toate valorile din ultimele 10 minute
        for (DataReceive d : receivedData) {
            if (!d.getTime().isBefore(startTime) && !d.getTime().isAfter(currentTime)) {
                consumption += d.getMeasurementValue();
                id = d.getDeviceId();
            }
        }

        dataService.update(id, currentTime, consumption);
        System.out.println("=== Consum ultimul minut (până la " + currentTime + ") ===");
        System.out.println("Total: " + consumption);
        System.out.println("----------------------------------------");
    }
}
