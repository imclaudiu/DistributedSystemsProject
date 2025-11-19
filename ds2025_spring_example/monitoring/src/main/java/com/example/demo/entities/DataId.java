package com.example.demo.entities;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Objects;
import java.util.UUID;

public class DataId  implements Serializable {
    private UUID deviceId;
    private LocalTime time;

    public DataId(){

    }

    public DataId(UUID deviceId, LocalTime time) {
        this.deviceId = deviceId;
        this.time = time;
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        DataId dataId = (DataId) o;
        return Objects.equals(deviceId, dataId.deviceId) && Objects.equals(time, dataId.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deviceId, time);
    }
}
