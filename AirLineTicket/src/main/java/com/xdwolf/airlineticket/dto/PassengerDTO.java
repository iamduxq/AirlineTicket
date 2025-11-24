package com.xdwolf.airlineticket.dto;

import java.time.LocalDateTime;

public class PassengerDTO extends AbstractDTO<PassengerDTO> {
    private TicketDTO ticket;
    private String fullname;
    private String gender;
    private LocalDateTime dayOfBirth;
    private String passportNumber;
    private String nationality;

    public TicketDTO getTicket() {
        return ticket;
    }

    public void setTicket(TicketDTO ticket) {
        this.ticket = ticket;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDateTime getDayOfBirth() {
        return dayOfBirth;
    }

    public void setDayOfBirth(LocalDateTime dayOfBirth) {
        this.dayOfBirth = dayOfBirth;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
}
