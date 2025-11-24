package com.xdwolf.airlineticket.service.impl;

import com.xdwolf.airlineticket.component.ServiceHelper;
import com.xdwolf.airlineticket.dto.TicketDTO;
import com.xdwolf.airlineticket.entity.FlightEntity;
import com.xdwolf.airlineticket.entity.TicketEntity;
import com.xdwolf.airlineticket.entity.UserEntity;
import com.xdwolf.airlineticket.service.ITicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService implements ITicketService {

    private final ServiceHelper serviceHelper;

    @Override
    public TicketDTO createTicket(TicketDTO dto) {
        List<UserEntity> users = serviceHelper.userRepository.findByUsername(dto.getUser().getUsername());
        if (users.isEmpty()) {
            throw new RuntimeException("Không tìm thấy người dùng có username: " + dto.getUser().getUsername());
        }
        UserEntity user = users.get(0);
        FlightEntity flight = serviceHelper.flightRepository.findFlightEntityById(dto.getFlight().getId());
        if (flight == null) {
            throw new RuntimeException("User not found");
        }

        TicketEntity ticket = new TicketEntity();
        ticket.setUser(user);
        ticket.setFlight(flight);
        ticket.setSeatNumber(dto.getSeatNumber());
        ticket.setBookingDate(LocalDateTime.now());
        ticket.setStatus((byte) 1);
        return serviceHelper.ticketConverter.convertToDTO(serviceHelper.ticketRepository.save(ticket));
    }
}
