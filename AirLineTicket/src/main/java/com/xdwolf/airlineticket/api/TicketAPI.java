package com.xdwolf.airlineticket.api;

import com.xdwolf.airlineticket.dto.TicketDTO;
import com.xdwolf.airlineticket.service.ITicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/ticket")
@RestController
@RequiredArgsConstructor
public class TicketAPI {
    private final ITicketService ticketService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody TicketDTO request) {
        return ResponseEntity.ok(ticketService.createTicket(request));
    }
}
