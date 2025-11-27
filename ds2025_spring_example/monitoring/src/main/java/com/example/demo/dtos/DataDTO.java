package com.example.demo.dtos;

import java.time.LocalTime;
import java.util.UUID;

public class DataDTO {
    private UUID uuid;
    private LocalTime time;
    private Integer hourlyValue;

    public DataDTO(UUID uuid, LocalTime time, Integer hourlyValue) {
        this.uuid = uuid;
        this.time = time;
        this.hourlyValue = hourlyValue;
    }

    public DataDTO(LocalTime time, Integer hourlyValue) {
        this.time = time;
        this.hourlyValue = hourlyValue;
    }

    public LocalTime getTime() {
        return time;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public Integer getHourlyValue() {
        return hourlyValue;
    }

    public void setHourlyValue(Integer hourlyValue) {
        this.hourlyValue = hourlyValue;
    }
}

