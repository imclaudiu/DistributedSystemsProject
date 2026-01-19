package com.example.demo.repositories;

import com.example.demo.entities.Data;
import com.example.demo.dtos.DataId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalTime;
import java.util.Optional;
import java.util.UUID;

public interface DataRepository extends JpaRepository<Data, DataId> {
    Optional<Data> findByDeviceIdAndTime(UUID deviceId, LocalTime localTime);
    void deleteAllByDeviceId(UUID deviceId);
//    void deleteByDeviceId(UUID deviceId);
}
