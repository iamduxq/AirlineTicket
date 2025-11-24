package com.xdwolf.airlineticket.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "booking")
public class BookingEntity extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "booking_user_id", foreignKey = @ForeignKey(name = "fk_booking_user"))
    private UserEntity user;

    @OneToOne
    @JoinColumn(name = "booking_ticket_id", foreignKey = @ForeignKey(name = "fk_booking_ticket"))
    private TicketEntity ticket;

    @Column(name = "booking_date")
    private LocalDateTime bookingDate = LocalDateTime.now();

    @Column(name = "status")
    private String status = "CONFIRMED";

    public UserEntity getUser() {
        return user;
    }
    public void setUser(UserEntity user) {
        this.user = user;
    }

    public TicketEntity getTicket() {
        return ticket;
    }
    public void setTicket(TicketEntity ticket) {
        this.ticket = ticket;
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
}
