package com.taskflow.model;

import java.time.LocalDateTime;

public class Appointment {
    private String id;
    private User user;
    private String service;
    private LocalDateTime dateTime;
    private String createdBy;

    public Appointment() {}

    public Appointment(String id, User user, String service, LocalDateTime dateTime) {
        this.id = id;
        this.user = user;
        this.service = service;
        this.dateTime = dateTime;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getService() { return service; }
    public void setService(String service) { this.service = service; }

    public LocalDateTime getDateTime() { return dateTime; }
    public void setDateTime(LocalDateTime dateTime) { this.dateTime = dateTime; }

    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }


    @Override
    public String toString() {
        return "Appointment{id='" + id + '\'' +
                ", user=" + user +
                ", service=" + service +
                ", dateTime=" + dateTime + "}";
    }
}
