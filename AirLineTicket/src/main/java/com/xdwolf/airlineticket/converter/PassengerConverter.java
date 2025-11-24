package com.xdwolf.airlineticket.converter;

import com.xdwolf.airlineticket.dto.FlightDTO;
import com.xdwolf.airlineticket.dto.PassengerDTO;
import com.xdwolf.airlineticket.entity.FlightEntity;
import com.xdwolf.airlineticket.entity.PassengerEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PassengerConverter {
    public PassengerDTO toDTO(PassengerEntity entity) {
        PassengerDTO dto = new PassengerDTO();
        dto.setId(entity.getId());
        dto.setFullname(entity.getFullname());
        dto.setGender(entity.getGender());

        if (entity.getDateOfBirth() != null) {
            dto.setDayOfBirth(entity.getDateOfBirth().atStartOfDay());
        }

        dto.setPassportNumber(entity.getPassportNumber());
        dto.setNationality(entity.getNationality());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setModifiedDate(entity.getModifiedDate());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setModifiedBy(entity.getModifiedBy());
        return dto;
    }

    public List<PassengerDTO> convertToDTOList(List<PassengerEntity> entity) {
        return entity.stream().map(this::toDTO).toList();
    }

    public PassengerEntity toEntity(PassengerDTO dto) {
        PassengerEntity entity = new PassengerEntity();
        entity.setFullname(dto.getFullname());
        entity.setGender(dto.getGender());
        entity.setPassportNumber(dto.getPassportNumber());
        entity.setNationality(dto.getNationality());

        if (dto.getDayOfBirth() != null) {
            entity.setDateOfBirth(dto.getDayOfBirth().toLocalDate());
        }
        entity.setCreatedDate(dto.getCreatedDate());
        entity.setModifiedDate(dto.getModifiedDate());
        entity.setCreatedBy(dto.getCreatedBy());
        entity.setModifiedBy(dto.getModifiedBy());
        return entity;
    }

    public void toEntity(PassengerDTO dto, PassengerEntity entity) {

        entity.setFullname(dto.getFullname());
        entity.setGender(dto.getGender());
        entity.setPassportNumber(dto.getPassportNumber());
        entity.setNationality(dto.getNationality());

        if (dto.getDayOfBirth() != null) {
            entity.setDateOfBirth(dto.getDayOfBirth().toLocalDate());
        }
    }
}
