package com.example.demo.services;

import com.example.demo.dtos.DeviceDTO;
import com.example.demo.dtos.DeviceDetailsDTO;
import com.example.demo.dtos.builders.DeviceBuilder;
import com.example.demo.entities.Device;
import com.example.demo.handlers.exceptions.model.ResourceNotFoundException;
import com.example.demo.repositories.DeviceRepository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.hibernate.sql.results.LoadingLogger.LOGGER;


@Service
public class DeviceService {

    private final DeviceRepository deviceRepository;

    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public DeviceDetailsDTO getDevice(UUID id){

        Device device = deviceRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(Device.class.getSimpleName() + "with id " + id));
        return DeviceBuilder.toDeviceDetailsDTO(device);
    }

    public Device insert(DeviceDetailsDTO deviceDetailsDTO){
        Device device = DeviceBuilder.toEntity(deviceDetailsDTO);
        deviceRepository.save(device);
        return deviceRepository.save(device);
    }

    public List<DeviceDTO> getAllDevices(){
        List<Device> devices = this.deviceRepository.findAll();
        return devices.stream().map(DeviceBuilder::toDeviceDTO).toList();
    }

    public void deleteDevice(UUID id){
        Device device = deviceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Device.class.getSimpleName() + " with id " + id));
        deviceRepository.deleteById(id);
    }

    public ResponseEntity<Device> patchDevice(@PathVariable UUID id, @RequestBody DeviceDetailsDTO deviceDetailsDTO){
        Device device = deviceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Device not found"));

        if(deviceDetailsDTO.getName() != null)
            device.setName(deviceDetailsDTO.getName());

        if(deviceDetailsDTO.getMaxConsumption() != null)
            device.setMaxConsumption(deviceDetailsDTO.getMaxConsumption());

        return ResponseEntity.ok(deviceRepository.save(device));
    }

//    public void deleteAllDevices(){
//        deviceRepository.deleteAll();
//    }

}
