package com.xdwolf.airlineticket.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class FlightDTO extends AbstractDTO<FlightDTO> {
    private Long departureAirportId;
    private Long arrivalAirportId;
    private String flightCode;
    private AirportDTO departureAirportName;
    private AirportDTO arrivalAirportName;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private String airline;
    private BigDecimal price;
    private Integer availableSeat;
    private Integer seatCapacity;
    private byte status;

    public Long getDepartureAirportId() {
        return departureAirportId;
    }

    public void setDepartureAirportId(Long departureAirportId) {
        this.departureAirportId = departureAirportId;
    }

    public Long getArrivalAirportId() {
        return arrivalAirportId;
    }

    public void setArrivalAirportId(Long arrivalAirportId) {
        this.arrivalAirportId = arrivalAirportId;
    }

    public Integer getSeatCapacity() {
        return seatCapacity;
    }

    public void setSeatCapacity(Integer seatCapacity) {
        this.seatCapacity = seatCapacity;
    }

    public String getFlightCode() {
        return flightCode;
    }

    public void setFlightCode(String flightCode) {
        this.flightCode = flightCode;
    }

    public AirportDTO getDepartureAirportName() {
        return departureAirportName;
    }

    public void setDepartureAirportName(AirportDTO departureAirportName) {
        this.departureAirportName = departureAirportName;
    }

    public AirportDTO getArrivalAirportName() {
        return arrivalAirportName;
    }

    public void setArrivalAirportName(AirportDTO arrivalAirportName) {
        this.arrivalAirportName = arrivalAirportName;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getAvailableSeat() {
        return availableSeat;
    }

    public void setAvailableSeat(Integer availableSeat) {
        this.availableSeat = availableSeat;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }
}
