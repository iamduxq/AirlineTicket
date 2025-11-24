package com.xdwolf.airlineticket.dto.requestDTO;

import com.xdwolf.airlineticket.dto.PassengerDTO;

import java.math.BigDecimal;
import java.util.List;

public class BookingRequestDTO {
    private String username;
    private Long flightId;
    private Integer seatNumber;
    private BigDecimal totalPrice;
    private List<PassengerDTO> passengers;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getFlightId() {
        return flightId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }

    public Integer getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(Integer seatNumber) {
        this.seatNumber = seatNumber;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<PassengerDTO> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<PassengerDTO> passengers) {
        this.passengers = passengers;
    }
}
