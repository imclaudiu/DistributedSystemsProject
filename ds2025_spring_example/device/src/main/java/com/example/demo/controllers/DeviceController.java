package com.example.demo.controllers;

import com.example.demo.dtos.DeviceDTO;
import com.example.demo.dtos.DeviceDetailsDTO;
import com.example.demo.entities.Device;
import com.example.demo.services.DeviceService;
import com.example.demo.services.KafkaProducerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/device")
@Validated
public class DeviceController {

    private final DeviceService deviceService;
    private final KafkaProducerService kafkaProducerService;

    public DeviceController(DeviceService deviceService, KafkaProducerService kafkaProducerService) {
        this.deviceService = deviceService;
        this.kafkaProducerService = kafkaProducerService;
    }

    @PostMapping("/add")
    public ResponseEntity<Void> create(@Valid @RequestBody DeviceDetailsDTO deviceDetailsDTO)
    {
        Device device = deviceService.insert(deviceDetailsDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("{/id}").buildAndExpand(device.getId()).toUri();
        kafkaProducerService.sendMessage(device.getId().toString());
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<DeviceDTO>> getAllDevices(){
        return ResponseEntity.ok(deviceService.getAllDevices());
    }

    @GetMapping("/get/{id}")
    public DeviceDetailsDTO getDevice(@PathVariable UUID id){
        return deviceService.getDevice(id);
    }

    @GetMapping("/findByOwnerId/{id}")
    public List<DeviceDetailsDTO> findByOwnerId(@PathVariable UUID id){
        return deviceService.findByOwnerId(id);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteDevice(@PathVariable UUID id)
    {
        deviceService.deleteDevice(id);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Device> updateDevice(@PathVariable UUID id, @RequestBody DeviceDetailsDTO deviceDetailsDTO){
        return deviceService.patchDevice(id, deviceDetailsDTO);
    }

    @DeleteMapping("/deleteAll")
    public void deleteAll(){deviceService.deleteAllDevices();}
}
