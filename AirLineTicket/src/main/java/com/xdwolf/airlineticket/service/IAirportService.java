package com.xdwolf.airlineticket.service;

import com.xdwolf.airlineticket.dto.AirportDTO;

import java.util.List;

public interface IAirportService {
    List<AirportDTO> findAll();
    AirportDTO save(AirportDTO airportDTO);
    AirportDTO update(AirportDTO airportDTO);
    void deleteById(Long id);
}
