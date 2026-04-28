package com.springboot.simplyfly.mapper;

import com.springboot.simplyfly.dto.AirportPageDto;
import com.springboot.simplyfly.dto.AirportReqDto;
import com.springboot.simplyfly.dto.AirportResDto;
import com.springboot.simplyfly.model.Airport;

public class AirportMapper {

    public static Airport mapToEntity(AirportReqDto airportReqDto){
        Airport airport = new Airport();
        airport.setName(airportReqDto.name());
        airport.setCity(airportReqDto.city());
        airport.setCode(airportReqDto.code());
        airport.setCountry(airportReqDto.country());
        return airport;
    }

    public static AirportResDto mapToDto(Airport airport){
        return new AirportResDto(
                airport.getName(),
                airport.getCode(),
                airport.getCity()
        );
    }
}