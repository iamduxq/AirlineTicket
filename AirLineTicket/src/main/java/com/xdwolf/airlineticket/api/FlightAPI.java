package com.xdwolf.airlineticket.api;

import com.xdwolf.airlineticket.dto.FlightDTO;
import com.xdwolf.airlineticket.service.IFlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequestMapping("/api/flight")
@RestController
@RequiredArgsConstructor
public class FlightAPI {
    private final IFlightService flightService;

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
//                                         @RequestParam(required = false) LocalDate returnDate,
//                                         @RequestParam(required = false) String tripType) {
//        if ("roundtrip".equalsIgnoreCase(tripType) && returnDate != null) {
//            return flightService.findR
//        }

        return flightService.searchFlights(from, to, date);
    }
}
