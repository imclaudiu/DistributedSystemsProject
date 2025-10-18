package com.example.demo.controllers;

import com.example.demo.dtos.DeviceDetailsDTO;
import com.example.demo.services.DeviceService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;


@RestController
@RequestMapping("device")
@Validated
public class DeviceController {
    DeviceService deviceService;

    @PostMapping
    public void create(@Valid @RequestBody DeviceDetailsDTO deviceDetailsDTO)
    {
        deviceService.insert(deviceDetailsDTO);
    }

}
