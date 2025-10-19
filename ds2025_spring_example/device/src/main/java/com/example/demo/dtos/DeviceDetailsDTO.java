package com.example.demo.dtos;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public class DeviceDetailsDTO {
    private UUID id;

    @NotBlank(message = "Device name required")
    private String name;
    private Integer maxConsumption;

    public DeviceDetailsDTO() {
    }

    public DeviceDetailsDTO(UUID id, String name, Integer maxConsumption) {
        this.id = id;
        this.name = name;
        this.maxConsumption = maxConsumption;
    }

    public DeviceDetailsDTO(String name, Integer maxConsumption)
    {
        this.name =name;
        this.maxConsumption = maxConsumption;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMaxConsumption() {
        return maxConsumption;
    }

    public void setMaxConsumption(Integer maxConsumption) {
        this.maxConsumption = maxConsumption;
    }
}
