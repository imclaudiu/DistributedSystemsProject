package com.example.simulator.entities;

import java.time.LocalTime;
import java.util.UUID;


public class Data {

    private UUID deviceId;
    private LocalTime time;
    private Integer measurementValue;

    @Override
    public String toString() {
        return "Data{" +
                "deviceId=" + deviceId +
                ", time=" + time +
                ", measurementValue=" + measurementValue +
                '}';
    }

    public Data(UUID deviceId, LocalTime time, Integer measurementValue) {
        this.deviceId = deviceId;
        this.time = time;
        this.measurementValue = measurementValue;
    }

    public Data(){

    }

    public UUID getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(UUID deviceId) {
        this.deviceId = deviceId;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public Integer getMeasurementValue() {
        return measurementValue;
    }

    public void setMeasurementValue(Integer measurementValue) {
        this.measurementValue = measurementValue;
    }
}

