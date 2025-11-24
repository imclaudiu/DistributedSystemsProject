package com.example.demo.services;

import com.example.demo.entities.Data;
import com.example.demo.entities.DataId;
import com.example.demo.repositories.DataRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DataService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataService.class);
    private final DataRepository dataRepository;

    @Autowired
    public DataService(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    public void insert(UUID uuid){
        Data data = new Data(uuid, LocalTime.now(), 0);
        this.dataRepository.save(data);
    }

    public void insertWithValues(UUID uuid, LocalTime localTime, Integer hourlyValue){
        if(this.dataRepository.findByDeviceIdAndTime(uuid, localTime).isPresent())
        {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Already exists!");
        }
        Data data = new Data(uuid, localTime, hourlyValue);
        this.dataRepository.save(data);
    }

    public Data update(UUID id, LocalTime time, Integer newMeasurementValue){
        Optional<Data> optionalData = dataRepository.findById(new DataId(id, time));
        if (optionalData.isEmpty()) {
            LOGGER.warn("Data with id {} not found", id);
            return null;
        }

        Data data = optionalData.get();
        if (newMeasurementValue != null) {
            data.setHourlyValue(newMeasurementValue);
        }

        return dataRepository.save(data);
    }

    @Transactional
    public void deleteDevicesByDeviceId(UUID id){
        try{
            dataRepository.deleteAllByDeviceId(id);
        } catch (Exception e) {
            throw new RuntimeException("Couldn't delete device(s):", e);
        }
    }

    public List<Data> getAll(){
        return dataRepository.findAll();
    }
}
