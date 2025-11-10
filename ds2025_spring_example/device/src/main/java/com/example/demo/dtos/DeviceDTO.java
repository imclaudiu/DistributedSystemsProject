package com.example.demo.dtos;

import java.util.UUID;

public class DeviceDTO {
    private UUID id;
    private String name;
    private UUID ownerID;

    public DeviceDTO() {
    }

    public DeviceDTO(UUID id, String name, UUID ownerID) {
        this.id = id;
        this.name = name;
        this.ownerID = ownerID;
    }

    public UUID getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(UUID ownerID) {
        this.ownerID = ownerID;
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
}
