package com.xdwolf.airlineticket.converter;

import com.xdwolf.airlineticket.dto.PaymentDTO;
import com.xdwolf.airlineticket.entity.PaymentEntity;
import com.xdwolf.airlineticket.entity.TicketEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PaymentConverter {
    public PaymentDTO convertToDTO(PaymentEntity entity) {
        PaymentDTO dto = new PaymentDTO();
        if (entity.getId() != null) {
            dto.setId(entity.getId());
        }
        dto.setTicketCode(entity.getTicket().getTicketCode());
        dto.setPaymentMethod(entity.getPaymentMethod());
        dto.setPaymentDate(entity.getPaymentDate());
        dto.setAmount(entity.getAmount());
        dto.setStatus(entity.getStatus());dto.setCreatedDate(entity.getCreatedDate());
        dto.setModifiedDate(entity.getModifiedDate());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setModifiedBy(entity.getModifiedBy());
        return dto;
    }

    public List<PaymentDTO> toPaymentDTOList(List<PaymentEntity> entities) {
        return entities.stream().map(this::convertToDTO).toList();
    }

    public PaymentEntity converToEntity(PaymentDTO dto) {
        PaymentEntity entity = new PaymentEntity();
        TicketEntity ticket = new TicketEntity();
        ticket.setTicketCode(dto.getTicketCode());
//        entity.setTicket(ticket);
        entity.setPaymentMethod(dto.getPaymentMethod());
        entity.setPaymentDate(dto.getPaymentDate());
        entity.setAmount(dto.getAmount());
        entity.setStatus(dto.getStatus());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setModifiedDate(entity.getModifiedDate());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setModifiedBy(entity.getModifiedBy());
        return entity;
    }
}
