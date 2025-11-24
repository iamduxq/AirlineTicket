package com.xdwolf.airlineticket.service.impl;

import com.xdwolf.airlineticket.component.ServiceHelper;
import com.xdwolf.airlineticket.dto.PassengerDTO;
import com.xdwolf.airlineticket.entity.BookingEntity;
import com.xdwolf.airlineticket.entity.PassengerEntity;
import com.xdwolf.airlineticket.entity.TicketEntity;
import com.xdwolf.airlineticket.service.IPassengerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PassengerService implements IPassengerService {

    private final ServiceHelper serviceHelper;

    @Override
    public PassengerDTO save(PassengerDTO dto) {
        PassengerEntity entity = serviceHelper.passengerConverter.toEntity(dto);
        if (dto.getTicket() != null && dto.getTicket().getId() != null) {
            TicketEntity ticket = serviceHelper.ticketRepository.findById(dto.getTicket().getId())
                    .orElseThrow(() -> new RuntimeException("Vé không tồn tại!"));
            entity.setTicket(ticket);
        }
        entity = serviceHelper.passengerRepository.save(entity);
        return serviceHelper.passengerConverter.toDTO(entity);
    }

    @Override
    public List<PassengerDTO> saveAll(List<PassengerDTO> passengers) {
        List<PassengerEntity> entities = passengers.stream().map(serviceHelper.passengerConverter::toEntity).toList();

        entities = serviceHelper.passengerRepository.saveAll(entities);
        return serviceHelper.passengerConverter.convertToDTOList(entities);
    }

    @Override
    public List<PassengerDTO> findByTicketId(Long ticketId) {
        TicketEntity ticket = serviceHelper.ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy vé"));

        List<PassengerEntity> pax = serviceHelper.passengerRepository.findByTicket(ticket);

        return serviceHelper.passengerConverter.convertToDTOList(pax);
    }
}
