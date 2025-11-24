package com.xdwolf.airlineticket.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "passenger")
public class PassengerEntity extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "passenger_ticket_id",
            foreignKey = @ForeignKey(name = "fk_passenger_ticket"))
    private TicketEntity ticket;

    @Column(name = "fullname")
    private String fullname;

    @Column(name = "gender")
    private String gender;

    @Column(name = "dateofbirth")
    private LocalDate dateOfBirth;

    @Column(name = "passportnumber")
    private String passportNumber;

    @Column(name = "nationality")
    private String nationality;

    public TicketEntity getTicket() {
        return ticket;
    }

    public void setTicket(TicketEntity ticket) {
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

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
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
