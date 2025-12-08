package com.xdwolf.airlineticket.service.impl;

import com.xdwolf.airlineticket.component.ServiceHelper;
import com.xdwolf.airlineticket.dto.FlightDTO;
import com.xdwolf.airlineticket.entity.AirportEntity;
import com.xdwolf.airlineticket.entity.FlightEntity;
import com.xdwolf.airlineticket.entity.UserEntity;
import com.xdwolf.airlineticket.service.IFlightService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightService implements IFlightService {

    private final ServiceHelper service;



    @Override
    @Transactional
    public FlightDTO findFlightById(Long id) {
        FlightEntity entity = service.flightRepository.findFlightEntityById(id);
        return service.flightConverter.convertToDTO(entity);
    }

    @Override
    public FlightDTO save(FlightDTO flightDTO) {
        AirportEntity departure = service.airportRepository.findById(flightDTO.getDepartureAirportId())
                .orElseThrow(() -> new RuntimeException("Departure airport not found"));
        AirportEntity arrival = service.airportRepository.findById(flightDTO.getArrivalAirportId())
                .orElseThrow(() -> new RuntimeException("Arrival airport not found"));

        FlightEntity entity = service.flightConverter.convertToEntity(flightDTO);
        entity.setDepartureAirport(departure);
        entity.setArrivalAirport(arrival);
        String code;
        do {
            code = entity.generateFlightCode();
        } while (service.flightRepository.existsByFightCode(code));
        entity.setFightCode(code);
        FlightEntity saved = service.flightRepository.save(entity);
        return service.flightConverter.convertToDTO(saved);
    }

    @Override
    public void updateAvailableSeat(Long flightId) {
        FlightEntity flight = service.flightRepository.findById(flightId)
                .orElseThrow(() -> new RuntimeException("Flight not found"));

        int capacity = flight.getSeatCapacity();
        long booked = service.ticketRepository.countActiveTickets(flightId);

        flight.setAvailableSeat(capacity - (int) booked);

        service.flightRepository.save(flight);
    }

    @Override
    public List<FlightDTO> searchFlights(String from, String to, String date) {
        LocalDate localDate = LocalDate.parse(date);
        LocalDateTime startOfDay = localDate.atStartOfDay();
        LocalDateTime endOfDay = localDate.plusDays(1).atStartOfDay();
        List<FlightEntity> flights = service.flightRepository.findByAirportsAndDate(from, to, startOfDay, endOfDay);
        return flights.stream().map(service.flightConverter::convertToDTO).toList();
    }
}
