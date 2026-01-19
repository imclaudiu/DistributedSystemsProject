package com.example.demo.dtos.builders;

import com.example.demo.dtos.DataDTO;
import com.example.demo.entities.Data;

public class DataBuilder {

    public static DataDTO toDataDTO(Data data){
        return new DataDTO(data.getTime(), data.getHourlyValue());
    }

    public static Data toEntity(DataDTO dataDTO){
        return new Data(dataDTO.getUuid(), dataDTO.getTime(), dataDTO.getHourlyValue());
    }
}
