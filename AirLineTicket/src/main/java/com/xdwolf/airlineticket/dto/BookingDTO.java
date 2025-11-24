package com.xdwolf.airlineticket.dto;

import java.time.LocalDateTime;
import java.util.List;

public class BookingDTO extends AbstractDTO<BookingDTO> {
    private TicketDTO ticket;
    private String username;
    private LocalDateTime bookingDate;
    private String status;
    private List<BookingDTO> passengers;


    public TicketDTO getTicket() {
        return ticket;
    }

    public void setTicket(TicketDTO ticket) {
        this.ticket = ticket;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDateTime bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<BookingDTO> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<BookingDTO> passengers) {
        this.passengers = passengers;
    }
}