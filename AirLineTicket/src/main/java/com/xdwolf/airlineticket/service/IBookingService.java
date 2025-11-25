package com.xdwolf.airlineticket.service;

import com.xdwolf.airlineticket.dto.BookingDTO;
import com.xdwolf.airlineticket.dto.PassengerDTO;
import com.xdwolf.airlineticket.dto.requestDTO.BookingRequestDTO;

import java.io.IOException;
import java.util.List;

public interface IBookingService {
    BookingDTO createBooking(Long flightId, String username);
    List<BookingDTO> findByUsername(String username);
    BookingDTO bookTicket(BookingRequestDTO request);
    BookingDTO updatePassenger(Long bookingId, PassengerDTO newPassenger);
    BookingDTO cancelBooking(Long bookingId);
    BookingDTO getBookingDetails(Long bookingId);
    byte[]exportTicketPdf(Long bookingId) throws IOException;
}
