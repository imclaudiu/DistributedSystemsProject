package com.example.demo.services;

import com.example.demo.dtos.DeviceDetailsDTO;
import com.example.demo.dtos.builders.DeviceBuilder;
import com.example.demo.entities.Device;
import com.example.demo.handlers.exceptions.model.ResourceNotFoundException;
import com.example.demo.repositories.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static org.hibernate.sql.results.LoadingLogger.LOGGER;


@Service
public class DeviceService {
    private final DeviceService deviceService;
    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    public DeviceService(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    public DeviceDetailsDTO findDevice(UUID id){

        Device device = deviceRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(Device.class.getSimpleName() + "with id " + id));
        return DeviceBuilder.toDeviceDetailsDTO(device);
    }

    public Device insert(DeviceDetailsDTO deviceDetailsDTO){
        Device device = DeviceBuilder.toEntity(deviceDetailsDTO);
        LOGGER.debug("Device with ID " + device.getId() + " inserted.");
        return deviceRepository.save(device);
    }

    public List<DeviceDetailsDTO> getAllDevices(){

    }

}
