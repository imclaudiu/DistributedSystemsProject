package com.example.demo.services;

import com.example.demo.entities.Data;
import com.example.demo.repositories.DataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
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

    public List<Data> getAll(){
        return dataRepository.findAll();
    }
}
