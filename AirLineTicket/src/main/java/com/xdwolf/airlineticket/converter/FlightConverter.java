package com.xdwolf.airlineticket.converter;

import com.xdwolf.airlineticket.dto.FlightDTO;
import com.xdwolf.airlineticket.entity.FlightEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FlightConverter {

    private final AirportConverter airportConverter;

    public FlightDTO convertToDTO(FlightEntity entity) {
        FlightDTO dto = new FlightDTO();
        if (entity.getId() != null) {
            dto.setId(entity.getId());
        }

        // Departure Airport
        if (entity.getDepartureAirport() != null) {
            dto.setDepartureAirportName(airportConverter.convertToDTO(entity.getDepartureAirport()));
            dto.setDepartureAirportId(entity.getDepartureAirport().getId());
        }

        // Arrival Airport
        if (entity.getArrivalAirport() != null) {
            dto.setArrivalAirportName(airportConverter.convertToDTO(entity.getArrivalAirport()));
            dto.setArrivalAirportId(entity.getArrivalAirport().getId());
        }
        dto.setFlightCode(entity.getFightCode());
        dto.setDepartureTime(entity.getDepartureTime());
        dto.setArrivalTime(entity.getArrivalTime());
        dto.setAirline(entity.getAirline());
        dto.setPrice(entity.getPrice());
        dto.setAvailableSeat(entity.getAvailableSeat());
        dto.setSeatCapacity(entity.getSeatCapacity());
        dto.setStatus(entity.getStatus());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setModifiedDate(entity.getModifiedDate());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setModifiedBy(entity.getModifiedBy());
        return dto;
    }

    public List<FlightDTO> convertToDTOList(List<FlightEntity> entity) {
        return entity.stream().map(this::convertToDTO).toList();
    }

    public FlightEntity convertToEntity(FlightDTO dto) {
        FlightEntity entity = new FlightEntity();
        entity.setFightCode(dto.getFlightCode());
        entity.setDepartureTime(dto.getDepartureTime());
        entity.setArrivalTime(dto.getArrivalTime());
        entity.setAirline(dto.getAirline());
        entity.setPrice(dto.getPrice());
        entity.setAvailableSeat(dto.getAvailableSeat());
        entity.setSeatCapacity(dto.getSeatCapacity());
        entity.setStatus(dto.getStatus());
        entity.setCreatedDate(dto.getCreatedDate());
        entity.setModifiedDate(dto.getModifiedDate());
        entity.setCreatedBy(dto.getCreatedBy());
        entity.setModifiedBy(dto.getModifiedBy());
        return entity;
    }
}