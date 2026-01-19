package com.example.demo.dtos;


import java.time.LocalTime;
import java.util.UUID;

public class DataReceive {

    private UUID deviceId;
    private LocalTime time;
    private Integer measurementValue;

    public DataReceive(UUID deviceId, LocalTime time, Integer measurementValue) {
        this.deviceId = deviceId;
        this.time = time;
        this.measurementValue = measurementValue;
    }

    public DataReceive(){

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

    @Override
    public String toString() {
        return "DataReceive{" +
                "deviceId=" + deviceId +
                ", time=" + time +
                ", measurementValue=" + measurementValue +
                '}';
    }
}

