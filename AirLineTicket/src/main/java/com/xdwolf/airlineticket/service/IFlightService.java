package com.xdwolf.airlineticket.service;

import com.xdwolf.airlineticket.dto.FlightDTO;

import java.util.List;

public interface IFlightService {
    FlightDTO findFlightById(Long id);
    FlightDTO save(FlightDTO flightDTO);
    List<FlightDTO> searchFlights(String from, String to, String date);
}
