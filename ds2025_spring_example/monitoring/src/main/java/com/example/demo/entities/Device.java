package com.example.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity
public class Device {

    @Id
    @JdbcTypeCode(SqlTypes.UUID)
    private UUID id;
    private Integer maxConsumption;

    public Device(UUID id, Integer maxConsumption) {
        this.id = id;
        this.maxConsumption = maxConsumption;
    }

    public Device(){

    }

    public Integer getMaxConsumption() {
        return maxConsumption;
    }

    public void setMaxConsumption(Integer maxConsumption) {
        this.maxConsumption = maxConsumption;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
