package com.xdwolf.airlineticket.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "notification")
public class NotificationEntity extends BaseEntity{

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    @ManyToOne
    @JoinColumn(name = "notification_user_id",
            foreignKey = @ForeignKey(name = "fk_notification_user"))
    private UserEntity user;

    @Column(name = "message")
    private String message;

    @Column(name = "type")
    private String type;

    @Column(name = "status")
    private byte status;
}
