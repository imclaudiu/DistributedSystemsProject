package com.example.demo.dtos;

import java.util.UUID;

public class DeviceMonitor {
    private UUID id;
    private Integer maxConsumption;

    public DeviceMonitor(UUID id, Integer maxConsumption) {
        this.id = id;
        this.maxConsumption = maxConsumption;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Integer getMaxConsumption() {
        return maxConsumption;
    }

    public void setMaxConsumption(Integer maxConsumption) {
        this.maxConsumption = maxConsumption;
    }

    @Override
    public String toString() {
        return "{id=" + id +
                ", maxConsumption=" + maxConsumption +
                '}';
    }
}
