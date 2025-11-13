package com.taskflow.model;

import java.time.LocalDateTime;

public class Reminder {
    private int id;
    private Appointment appointment;
    private LocalDateTime reminderTime;
    private boolean sent;

    public Reminder() {}

    public Reminder(int id, Appointment appointment, LocalDateTime reminderTime, boolean sent) {
        this.id = id;
        this.appointment = appointment;
        this.reminderTime = reminderTime;
        this.sent = sent;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Appointment getAppointment() { return appointment; }
    public void setAppointment(Appointment appointment) { this.appointment = appointment; }

    public LocalDateTime getReminderTime() { return reminderTime; }
    public void setReminderTime(LocalDateTime reminderTime) { this.reminderTime = reminderTime; }

    public boolean isSent() { return sent; }
    public void setSent(boolean sent) { this.sent = sent; }

    @Override
    public String toString() {
        return "Reminder{id=" + id +
                ", appointment=" + appointment +
                ", reminderTime=" + reminderTime +
                ", sent=" + sent + "}";
    }
}
