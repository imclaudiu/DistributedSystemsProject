package com.example.demo.dtos.builders;

import com.example.demo.dtos.DeviceDTO;
import com.example.demo.dtos.DeviceDetailsDTO;
import com.example.demo.dtos.DeviceMonitor;
import com.example.demo.entities.Device;

public class DeviceBuilder {

    public static DeviceDetailsDTO toDeviceDetailsDTO(Device device){
        return new DeviceDetailsDTO(device.getId(), device.getName(), device.getMaxConsumption(), device.getOwnerID());
    }

    public static DeviceDTO toDeviceDTO(Device device){
        return new DeviceDTO(device.getId(), device.getName(), device.getOwnerID());
    }

    public static DeviceMonitor toDeviceMonitor(Device device){
        return new DeviceMonitor(device.getId(), device.getMaxConsumption());
    }

    public static Device toEntity(DeviceDetailsDTO deviceDetailsDTO){
        return new Device(deviceDetailsDTO.getName(), deviceDetailsDTO.getMaxConsumption(), deviceDetailsDTO.getOwnerID());
    }
}
