package com.xdwolf.airlineticket.converter;

import com.xdwolf.airlineticket.dto.TicketDTO;
import com.xdwolf.airlineticket.entity.TicketEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TicketConverter {

    private final FlightConverter flightConverter;
    private final UserConverter userConverter;
    private final PassengerConverter passengerConverter;

    public TicketDTO convertToDTO(TicketEntity entity) {
        TicketDTO dto = new TicketDTO();

        if (entity.getId() != null) {
            dto.setId(entity.getId());
        }

        if (entity.getFlight() != null) {
            dto.setFlight(flightConverter.convertToDTO(entity.getFlight()));
        }

        if (entity.getUser() != null) {
            dto.setUser(userConverter.convertToDTO(entity.getUser()));
        }

        if (entity.getPassenger() != null) {
            dto.setPassengers(entity.getPassenger().stream().map(passengerConverter::toDTO).toList());
        }

        dto.setTicketCode(entity.getTicketCode());
        dto.setBookingDate(entity.getBookingDate());
        dto.setSeatNumber(entity.getSeatNumber());
        dto.setStatus(entity.getStatus());
        dto.setTotalPrice(entity.getTotalPrice());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setModifiedDate(entity.getModifiedDate());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setModifiedBy(entity.getModifiedBy());
        return dto;
    }

    public List<TicketDTO> toDTOList(List<TicketEntity> entities) {
        if (entities == null) return List.of();
        return entities.stream().map(this::convertToDTO).toList();
    }

    public TicketEntity convertToEntity(TicketDTO dto) {
        TicketEntity entity = new TicketEntity();
        entity.setFlight(flightConverter.convertToEntity(dto.getFlight()));
        entity.setUser(userConverter.convertToEntity(dto.getUser()));
        entity.setBookingDate(dto.getBookingDate());
        entity.setSeatNumber(dto.getSeatNumber());
        entity.setStatus(dto.getStatus());
        entity.setTicketCode(dto.getTicketCode());
        entity.setTotalPrice(dto.getTotalPrice());
        entity.setCreatedDate(dto.getCreatedDate());
        entity.setModifiedDate(dto.getModifiedDate());
        entity.setCreatedBy(dto.getCreatedBy());
        entity.setModifiedBy(dto.getModifiedBy());
        return entity;
    }

    public TicketEntity convertToEntity(TicketEntity entity, TicketDTO dto) {
        entity.setFlight(flightConverter.convertToEntity(dto.getFlight()));
        entity.setUser(userConverter.convertToEntity(dto.getUser()));
        entity.setBookingDate(dto.getBookingDate());
        entity.setSeatNumber(dto.getSeatNumber());
        entity.setStatus(dto.getStatus());
        entity.setTicketCode(dto.getTicketCode());
        entity.setTotalPrice(dto.getTotalPrice());
        entity.setCreatedDate(dto.getCreatedDate());
        entity.setModifiedDate(dto.getModifiedDate());
        entity.setCreatedBy(dto.getCreatedBy());
        entity.setModifiedBy(dto.getModifiedBy());
        return entity;
    }
}
