package com.example.demo.services;

import com.example.demo.entities.Device;
import com.example.demo.handlers.exceptions.model.ResourceNotFoundException;
import com.example.demo.repositories.DeviceRepository;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class DeviceService {
    private final DeviceRepository deviceRepository;

    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public HttpStatus insert(Device device){
        this.deviceRepository.save(device);
        return HttpStatus.OK;
    }

    public Device findById(UUID uuid){
        return this.deviceRepository.findById(uuid).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public HttpStatus delete(UUID id){
        if(deviceRepository.findById(id).isPresent()) {
            this.deviceRepository.deleteById(id);
            return HttpStatus.OK;
        }
        else
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public List<Device> getAll(){
        return deviceRepository.findAll();
    }


}
