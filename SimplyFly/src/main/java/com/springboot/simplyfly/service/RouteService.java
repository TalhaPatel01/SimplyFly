package com.springboot.simplyfly.service;

import com.springboot.simplyfly.dto.RouteAddReqDto;
import com.springboot.simplyfly.dto.RoutePageResDto;
import com.springboot.simplyfly.dto.RouteResDto;
import com.springboot.simplyfly.mapper.RouteMapper;
import com.springboot.simplyfly.model.Airport;
import com.springboot.simplyfly.model.Route;
import com.springboot.simplyfly.repository.AirportRepository;
import com.springboot.simplyfly.repository.RouteRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RouteService {
    private final RouteRepository routeRepository;
    private final AirportRepository airportRepository;

    public void addRoute(RouteAddReqDto routeAddReqDto) {
        Airport sourceAirport = airportRepository.findByCode(routeAddReqDto.sourceCode());
        Airport destinationAirport = airportRepository.findByCode(routeAddReqDto.destinationCode());

        Route route = new Route();
        route.setSourceAirport(sourceAirport);
        route.setDestinationAirport(destinationAirport);
        route.setDistance(routeAddReqDto.distance());
        routeRepository.save(route);
    }

    public RoutePageResDto getAllRoutes(int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        Page<Route> pageRoute = routeRepository.findAll(pageable);
        long totalRecords = pageRoute.getTotalElements();
        long totalPages = pageRoute.getTotalPages();

        List<Route> routeList = pageRoute.toList();
        List<RouteResDto> list = routeList
                .stream()
                .map(RouteMapper::mapToDto)
                .toList();

        return new RoutePageResDto(
               list,
               totalRecords,
               totalPages
        );
    }
}