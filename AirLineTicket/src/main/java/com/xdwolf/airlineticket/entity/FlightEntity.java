package com.xdwolf.airlineticket.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Entity
@Table(name = "flight")
public class FlightEntity extends BaseEntity {
    @Column(name = "flightcode", unique = true)
    private String fightCode;

    @ManyToOne
    @JoinColumn(name = "departure_airport_id",
            foreignKey = @ForeignKey(name = "fk_flight_airport_departureAirport"))
    private AirportEntity departureAirport;

    @ManyToOne
    @JoinColumn(name = "arrivel_airport_id",
            foreignKey = @ForeignKey(name = "fk_flight_airport_arrivalAirport"))
    private AirportEntity arrivalAirport;

    @Column(name = "departuretime")
    private LocalDateTime departureTime;

    @Column(name = "arrivaltime")
    private LocalDateTime arrivalTime;

    @Column(name = "airline")
    private String airline;

    @Column(name = "seatcapacity")
    private Integer seatCapacity;

    @Column(name = "availableseat")
    private Integer availableSeat;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "status")
    private byte status;

    public List<TicketEntity> getTickets() {
        return tickets;
    }

    public void setTickets(List<TicketEntity> tickets) {
        this.tickets = tickets;
    }

    @OneToMany(mappedBy = "flight")
    private List<TicketEntity> tickets;

    private String randomLetters(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append((char) ('A' + random.nextInt(26)));
        }
        return sb.toString();
    }

    public String generateFlightCode() {
        String letters = randomLetters(2);
        String numbers = String.format("%06d", new Random().nextInt(1_000_000));
        return letters + numbers;
    }

    @PrePersist
    public void generateFlightCodedIfAbsent() {
        if (fightCode == null || fightCode.isEmpty()) {
            fightCode = generateFlightCode();
        }
    }

    public String getFightCode() {
        return fightCode;
    }

    public void setFightCode(String fightCode) {
        this.fightCode = fightCode;
    }

    public AirportEntity getDepartureAirport() {
        return departureAirport;
    }

    public void setDepartureAirport(AirportEntity departureAirport) {
        this.departureAirport = departureAirport;
    }

    public AirportEntity getArrivalAirport() {
        return arrivalAirport;
    }

    public void setArrivalAirport(AirportEntity arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public Integer getSeatCapacity() {
        return seatCapacity;
    }

    public void setSeatCapacity(Integer seatCapacity) {
        this.seatCapacity = seatCapacity;
    }

    public Integer getAvailableSeat() {
        return availableSeat;
    }

    public void setAvailableSeat(Integer availableSeat) {
        this.availableSeat = availableSeat;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }
}
