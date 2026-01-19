package com.example.demo.controllers;

import com.example.demo.entities.Data;
import com.example.demo.entities.Device;
import com.example.demo.services.DataService;
import com.example.demo.services.DeviceService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/data")
@Validated
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", allowCredentials = "true")
public class DataController {
    private final DataService dataService;
    private final DeviceService deviceService;

    public DataController(DataService dataService, DeviceService deviceService) {
        this.dataService = dataService;
        this.deviceService = deviceService;
    }

    @GetMapping("/getAll")
    public List<Data> getAll(){
       return this.dataService.getAll();
    }

    @GetMapping("/devices")
    public List<Device> getAllDevices(){
        return this.deviceService.getAll();
    }

    @DeleteMapping("/deleteDevice")
    public HttpStatus deleteDevice(@RequestParam UUID id){
        return this.deviceService.delete(id);
    }

    @DeleteMapping("/deleteDevices/{id}")
    public void deleteDevices(@PathVariable UUID id){
        dataService.deleteDevicesByDeviceId(id);
    }
}
