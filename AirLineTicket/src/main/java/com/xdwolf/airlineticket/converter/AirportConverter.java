package com.xdwolf.airlineticket.converter;

import com.xdwolf.airlineticket.dto.AirportDTO;
import com.xdwolf.airlineticket.entity.AirportEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AirportConverter {
    public AirportDTO convertToDTO(AirportEntity entity) {
        AirportDTO dto = new AirportDTO();
        if (entity.getId() != null) {
            dto.setId(entity.getId());
        }
        dto.setName(entity.getName());
        dto.setCode(entity.getCode());
        dto.setCity(entity.getCity());
        dto.setCountry(entity.getCountry());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setModifiedDate(entity.getModifiedDate());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setModifiedBy(entity.getModifiedBy());
        return dto;
    }

    public List<AirportDTO> convertToDTOList(List<AirportEntity> entity) {
        return entity.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public AirportEntity convertToEntity(AirportDTO dto) {
        AirportEntity entity = new AirportEntity();
        entity.setName(dto.getName());
        entity.setCode(dto.getCode());
        entity.setCity(dto.getCity());
        entity.setCountry(dto.getCountry());
        entity.setCreatedDate(dto.getCreatedDate());
        entity.setModifiedDate(dto.getModifiedDate());
        entity.setCreatedBy(dto.getCreatedBy());
        entity.setModifiedBy(dto.getModifiedBy());
        return entity;
    }
}
