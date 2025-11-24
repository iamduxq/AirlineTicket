package com.xdwolf.airlineticket.converter;
import com.xdwolf.airlineticket.component.ServiceHelper;
import com.xdwolf.airlineticket.dto.BookingDTO;
import com.xdwolf.airlineticket.entity.BookingEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BookingConverter {

    private final PassengerConverter passengerConverter;
    private final TicketConverter ticketConverter;

    public BookingDTO toDTO(BookingEntity entity) {
        BookingDTO dto = new BookingDTO();
        dto.setId(entity.getId());
        dto.setStatus(entity.getStatus());
        dto.setBookingDate(entity.getBookingDate());

        if (entity.getUser() != null) {
//            dto.setUsername(entity.getUser().getFullname());
            dto.setUsername(entity.getUser().getUsername());
        }

        if (entity.getTicket() != null) {
            dto.setTicket(ticketConverter.convertToDTO(entity.getTicket()));
        }
        return dto;
    }

    public List<BookingDTO> toDTOList(List<BookingEntity> entities) {
        return entities.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
