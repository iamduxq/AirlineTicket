package com.xdwolf.airlineticket.api;

import com.xdwolf.airlineticket.dto.PassengerDTO;
import com.xdwolf.airlineticket.service.IPassengerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8888")
@RequestMapping("api/passenger")
@RestController
@RequiredArgsConstructor
public class PassengerAPI {
    private final IPassengerService passengerService;

    @PostMapping("/craete")
    public PassengerDTO createPassenger(@RequestBody PassengerDTO dto) {
        return passengerService.save(dto);
    }

    @PostMapping("create-list")
    public List<PassengerDTO> createList(@RequestBody List<PassengerDTO> list) {
        return passengerService.saveAll(list);
    }

    @GetMapping("/ticket/{ticketId}")
    public List<PassengerDTO> getTicketById(@PathVariable Long ticketId) {
        return passengerService.findByTicketId(ticketId);
    }
}
