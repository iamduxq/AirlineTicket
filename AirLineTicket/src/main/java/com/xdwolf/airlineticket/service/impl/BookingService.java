package com.xdwolf.airlineticket.service.impl;

import com.xdwolf.airlineticket.component.ServiceHelper;
import com.xdwolf.airlineticket.dto.BookingDTO;
import com.xdwolf.airlineticket.dto.PassengerDTO;
import com.xdwolf.airlineticket.dto.requestDTO.BookingRequestDTO;
import com.xdwolf.airlineticket.entity.*;
import com.xdwolf.airlineticket.service.IBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService implements IBookingService {

    private final ServiceHelper serviceHelper;

    @Override
    public BookingDTO createBooking(Long flightId, String username) {
        UserEntity user = serviceHelper.userRepository.findFirstByUsername(username)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));

        FlightEntity flight = serviceHelper.flightRepository.findById(flightId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy chuyến bay!"));

        if (flight.getAvailableSeat() <= 0) {
            throw new RuntimeException("Hết ghế trống cho chuyến bay này!");
        }

        flight.setAvailableSeat(flight.getAvailableSeat() - 1);
        serviceHelper.flightRepository.save(flight);

        BookingEntity booking = new BookingEntity();
        booking.setUser(user);
//        booking.setFlight(flight);
        booking.setBookingDate(LocalDateTime.now());
        booking.setStatus("CONFIRMED");
        serviceHelper.bookingRepository.save(booking);
        return serviceHelper.bookingConverter.toDTO(booking);
    }

    @Override
    public List<BookingDTO> findByUsername(String username) {
        List<BookingEntity> bookings = serviceHelper.bookingRepository.findByUser_Username(username);
        return serviceHelper.bookingConverter.toDTOList(bookings);
    }

    @Override
    @Transactional
    public BookingDTO bookTicket(BookingRequestDTO request) {
        UserEntity user = serviceHelper.userRepository
                .findFirstByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));

        FlightEntity flight = serviceHelper.flightRepository.findById(request.getFlightId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy chuyến bay!"));

        if (flight.getAvailableSeat() <= 0) {
            throw new RuntimeException("Hiện giờ chuyến bay đã hết ghế trống");
        }
        flight.setAvailableSeat(flight.getAvailableSeat() - 1);
        serviceHelper.flightRepository.save(flight);

        TicketEntity ticket = new TicketEntity();
        ticket.setUser(user);
        ticket.setFlight(flight);
        ticket.setSeatNumber(request.getSeatNumber());
        ticket.setTotalPrice(request.getTotalPrice());
        ticket.setStatus((byte) 1);
        ticket.setBookingDate(LocalDateTime.now());
        ticket = serviceHelper.ticketRepository.save(ticket);

        for (PassengerDTO p : request.getPassengers()) {
            PassengerEntity pe = serviceHelper.passengerConverter.toEntity(p);
            pe.setTicket(ticket);
            serviceHelper.passengerRepository.save(pe);
        }

        BookingEntity booking = new BookingEntity();
        booking.setTicket(ticket);
        booking.setUser(user);
        booking.setStatus("CONFIRMED");
        booking.setBookingDate(LocalDateTime.now());
        ticket.setBooking(booking);
        booking = serviceHelper.bookingRepository.save(booking);
        return serviceHelper.bookingConverter.toDTO(booking);
    }


    @Override
    @Transactional
    public BookingDTO updatePassenger(Long bookingId, PassengerDTO newPassenger) {
        BookingEntity booking = serviceHelper.bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy vé đặt"));

//        if (!booking.getStatus().equals("CONFIRMED")) {
//            throw new RuntimeException("Chỉ có thể sửa thông tin khi vé ở trạng thái xác nhận!");
//        }

        TicketEntity ticket = booking.getTicket();
        if (ticket == null) {
            throw new RuntimeException("Booking không có thông tin vé!");
        }

        PassengerEntity passenger = serviceHelper.passengerRepository.findFirstByTicket(ticket);
        passenger.setFullname(newPassenger.getFullname());
        passenger.setGender(newPassenger.getGender());
        passenger.setPassportNumber(newPassenger.getPassportNumber());
        passenger.setNationality(newPassenger.getNationality());
        passenger.setDateOfBirth(newPassenger.getDayOfBirth().toLocalDate());
        serviceHelper.passengerRepository.save(passenger);
        return serviceHelper.bookingConverter.toDTO(booking);
    }

    @Override
    @Transactional
    public BookingDTO cancelBooking(Long bookingId) {
        BookingEntity booking = serviceHelper.bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy vé đã đật"));

        TicketEntity ticket = booking.getTicket();
        if (ticket == null) {
            throw new RuntimeException("Vé không tồn tại");
        }

        FlightEntity flight = ticket.getFlight();
        if (flight != null) {
            flight.setAvailableSeat(flight.getAvailableSeat() + 1);
            serviceHelper.flightRepository.save(flight);
        }

        booking.setStatus("CANCELLED");
        ticket.setStatus((byte) 0);
        serviceHelper.ticketRepository.save(ticket);
        serviceHelper.bookingRepository.save(booking);
        return serviceHelper.bookingConverter.toDTO(booking);
    }

    @Override
    public BookingDTO getBookingDetails(Long bookingId) {
        BookingEntity booking = serviceHelper.bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy vé đã đặt"));
        return serviceHelper.bookingConverter.toDTO(booking);
    }

    @Override
    public byte[] exportTicketPdf(Long bookingId) {

        return new byte[0];
    }
}
