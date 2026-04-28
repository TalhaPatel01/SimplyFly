package com.springboot.simplyfly.mapper;

import com.springboot.simplyfly.dto.RouteResDto;
import com.springboot.simplyfly.model.Route;

public class RouteMapper {

    public static RouteResDto mapToDto(Route route){
        return new RouteResDto(
                route.getRoute_id(),
                route.getSourceAirport().getCity(),
                route.getDestinationAirport().getCity(),
                route.getDistance()
        );
    }
}