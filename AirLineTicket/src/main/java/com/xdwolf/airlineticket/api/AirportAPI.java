package com.xdwolf.airlineticket.api;

import com.xdwolf.airlineticket.dto.AirportDTO;
import com.xdwolf.airlineticket.service.IAirportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/airport")
@RestController
@RequiredArgsConstructor
public class AirportAPI {

    private final IAirportService airportService;

    @GetMapping(value = "/find")
    public List<AirportDTO> getAirport() {
        return airportService.findAll();
    }

    @PostMapping(value = "/create")
    public AirportDTO createAirport(@RequestBody AirportDTO dto) {
        return airportService.save(dto);
    }

    @PutMapping(value = "/update/{id}")
    public AirportDTO updateAirport(@RequestBody AirportDTO dto, @PathVariable Long id) {
        dto.setId(id);
        return airportService.update(dto);
    }

    @DeleteMapping(value = "/delete/{id}")
    public void deleteAirport(@PathVariable Long id) {
        airportService.deleteById(id);
    }

}
