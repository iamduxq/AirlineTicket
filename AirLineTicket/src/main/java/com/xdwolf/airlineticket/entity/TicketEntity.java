package com.xdwolf.airlineticket.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Entity
@Table(name = "ticket")
public class TicketEntity extends BaseEntity{
    @Column(name = "ticketcode")
    private String ticketCode;

    @ManyToOne
    @JoinColumn(name = "ticket_username_id",
    foreignKey = @ForeignKey(name = "fk_ticket_user"))
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "ticket_flight_id", foreignKey = @ForeignKey(name = "fk_ticket_flight"))
    private FlightEntity flight;

    @Column(name = "bookingdate")
    private LocalDateTime bookingDate;

    @Column(name = "seatnumber")
    private Integer seatNumber;

    @Column(name = "status")
    private byte status;

    @Column(name = "totalprice")
    private BigDecimal totalPrice;

    @OneToOne(mappedBy = "ticket")
    private PaymentEntity payment;

    @OneToMany(mappedBy = "ticket")
    private List<PassengerEntity> passenger;

    @OneToOne(mappedBy = "ticket")
    private BookingEntity booking;

    @Transient
    public String getFirstPassengerName() {
        try {
            if (passenger == null || passenger.isEmpty()) {
                return "Không có tên";
            } return passenger.get(0).getFullname();
        } catch (Exception e) {
            return "Không có tên";
        }
    }

    private String radomLetters(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append((char) ('A' + random.nextInt(26)));
        }
        return sb.toString();
    }

    public String generateTicketCode() {
        String letters = radomLetters(2);
        String numbers = String.format("%06d" , new Random().nextInt(1_000_000));
        return letters + numbers;
    }

    @PrePersist
    public void generateTicketCodedIfAbsent() {
        if (ticketCode == null || ticketCode.isEmpty()) {
            ticketCode = generateTicketCode();
        }
    }

    public BookingEntity getBooking() {
        return booking;
    }

    public void setBooking(BookingEntity booking) {
        this.booking = booking;
    }

    public FlightEntity getFlight() {
        return flight;
    }

    public void setFlight(FlightEntity flight) {
        this.flight = flight;
    }

    public List<PassengerEntity> getPassenger() {
        return passenger;
    }

    public void setPassenger(List<PassengerEntity> passenger) {
        this.passenger = passenger;
    }

    public PaymentEntity getPayment() {
        return payment;
    }

    public void setPayment(PaymentEntity payment) {
        this.payment = payment;
    }

    public String getTicketCode() {
        return ticketCode;
    }

    public void setTicketCode(String ticketCode) {
        this.ticketCode = ticketCode;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public LocalDateTime getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDateTime bookingDate) {
        this.bookingDate = bookingDate;
    }

    public Integer getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(Integer seatNumber) {
        this.seatNumber = seatNumber;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
