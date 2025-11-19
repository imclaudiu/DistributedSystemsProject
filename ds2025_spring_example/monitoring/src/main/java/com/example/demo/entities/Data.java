package com.example.demo.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@IdClass(DataId.class)
public class Data implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @JdbcTypeCode(SqlTypes.UUID)
    private UUID deviceId;

    @Id
//    @Column(name = "time", nullable = false)
    private LocalTime time;

    @Column(name = "measurementValue", nullable = false)
    private Integer hourlyValue;

    public Data(UUID deviceId, LocalTime time, Integer hourlyValue) {
        this.hourlyValue = hourlyValue;
        this.time = time;
        this.deviceId = deviceId;
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

    public Integer getHourlyValue() {
        return hourlyValue;
    }

    public void setHourlyValue(Integer measurementValue) {
        this.hourlyValue = measurementValue;
    }
}

