package com.xdwolf.airlineticket.api;

import com.xdwolf.airlineticket.component.ServiceHelper;
import com.xdwolf.airlineticket.dto.FlightDTO;
import com.xdwolf.airlineticket.entity.AirportEntity;
import com.xdwolf.airlineticket.entity.FlightEntity;
import com.xdwolf.airlineticket.service.IFlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RequestMapping("/api/flight")
@RestController
@RequiredArgsConstructor
public class FlightAPI {
    private final IFlightService flightService;
    private final ServiceHelper serviceHelper;

    @GetMapping("/{id}")
    FlightDTO getFlightById(@PathVariable Long id) {
        return flightService.findFlightById(id);
    }

    @PostMapping("")
    FlightDTO createFlight(@RequestBody FlightDTO flightDTO) {
        return flightService.save(flightDTO);
    }

    @GetMapping("/search")
    public List<FlightDTO> searchFlights(@RequestParam String from,
                                         @RequestParam String to,
                                         @RequestParam String date) {
        return flightService.searchFlights(from, to, date);
    }

    @PostMapping("/save")
    public String saveFlight(@RequestParam(required = false) Long id,
                             @RequestParam Long departureAirportId,
                             @RequestParam Long arrivalAirportId,
                             @RequestParam BigDecimal price,
                             @RequestParam String airline,
                             @RequestParam int seatCapacity,
                             @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                 LocalDateTime departureTime,
                             @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                             LocalDateTime arrivalTime) {
        FlightEntity flight;
        if (id != null) {
            flight = serviceHelper.flightRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy chuyến bay!"));
        } else {
            flight = new FlightEntity();
            flight.setStatus((byte) 1);
        }

        // Lấy sân bay
        AirportEntity dep = serviceHelper.airportRepository.findById(departureAirportId)
                .orElseThrow(() -> new RuntimeException("Điểm đi không tìm thấy!"));

        AirportEntity arr = serviceHelper.airportRepository.findById(arrivalAirportId)
                .orElseThrow(() -> new RuntimeException("Điểm đến không tìm thấy!"));
        flight.setDepartureAirport(dep);
        flight.setArrivalAirport(arr);
        flight.setSeatCapacity(seatCapacity);
        flight.setDepartureTime(departureTime);
        flight.setArrivalTime(arrivalTime);
        flight.setPrice(price);
        flight.setAirline(airline);
        serviceHelper.flightRepository.save(flight);
        flightService.updateAvailableSeat(flight.getId());
        return "/";
    }
}
