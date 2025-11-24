package com.xdwolf.airlineticket.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "airport")
public class AirportEntity extends BaseEntity{
    @Column
    private String code;

    @Column
    private String name;

    @Column
    private String city;

    @Column
    private String country;

    @OneToMany(mappedBy = "departureAirport")
    private List<FlightEntity> departureFlights;

    @OneToMany(mappedBy = "arrivalAirport")
    private List<FlightEntity> arrivalFlights;

    public List<FlightEntity> getDepartureFlights() {
        return departureFlights;
    }

    public void setDepartureFlights(List<FlightEntity> departureFlights) {
        this.departureFlights = departureFlights;
    }

    public List<FlightEntity> getArrivalFlights() {
        return arrivalFlights;
    }

    public void setArrivalFlights(List<FlightEntity> arrivalFlights) {
        this.arrivalFlights = arrivalFlights;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
