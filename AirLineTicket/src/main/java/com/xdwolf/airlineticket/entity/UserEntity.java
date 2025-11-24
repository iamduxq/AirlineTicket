package com.xdwolf.airlineticket.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "user")
public class UserEntity extends BaseEntity {
    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String fullname;

    @Column
    private String gender;

    @Column
    private LocalDateTime birthDate;

    @Column
    private String email;

    @Column
    private String phone;

    @Column
    private String role;

    @Column
    private byte status;

    @OneToMany(mappedBy = "user")
    private List<TicketEntity> ticket;

    @OneToMany(mappedBy = "user")
    private List<NotificationEntity> notification;

    @OneToMany(mappedBy = "user")
    private List<BookingEntity> bookings;

    public List<BookingEntity> getBookings() {
        return bookings;
    }

    public void setBookings(List<BookingEntity> bookings) {
        this.bookings = bookings;
    }

    public LocalDateTime getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDateTime birthDate) {
        this.birthDate = birthDate;
    }

    public List<NotificationEntity> getNotification() {
        return notification;
    }

    public void setNotification(List<NotificationEntity> notification) {
        this.notification = notification;
    }

    public List<TicketEntity> getTicket() {
        return ticket;
    }

    public void setTicket(List<TicketEntity> ticket) {
        this.ticket = ticket;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }
}
