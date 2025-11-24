package com.xdwolf.airlineticket.service.impl;

import com.xdwolf.airlineticket.component.ServiceHelper;
import com.xdwolf.airlineticket.dto.AirportDTO;
import com.xdwolf.airlineticket.entity.AirportEntity;
import com.xdwolf.airlineticket.service.IAirportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AirportService implements IAirportService {

    private final ServiceHelper service;

    @Override
    public List<AirportDTO> findAll() {
        return service.airportConverter.convertToDTOList(service.airportRepository.findAll());
    }

    @Override
    public AirportDTO save(AirportDTO airportDTO) {
        AirportEntity airportEntity = service.airportConverter.convertToEntity(airportDTO);
        AirportEntity saved = service.airportRepository.save(airportEntity);
        return service.airportConverter.convertToDTO(saved);
    }

    @Override
    public AirportDTO update(AirportDTO airportDTO) {
        // If id không tồn tại
        if (airportDTO.getId() == null) {
            return save(airportDTO);
        }
        AirportEntity existing = service.airportRepository.findById(airportDTO.getId()).orElseThrow();
        existing.setName(airportDTO.getName());
        existing.setCode(airportDTO.getCode());
        existing.setCity(airportDTO.getCity());
        existing.setCountry(airportDTO.getCountry());
        AirportEntity updateAirport = service.airportRepository.save(existing);
        return service.airportConverter.convertToDTO(updateAirport);
    }

    @Override
    public void deleteById(Long id) {
        service.airportRepository.deleteById(id);
    }
}
