package com.taskflow.service;

import com.taskflow.model.Appointment;
import java.time.LocalDateTime;

public interface ReminderService {
    void sendReminder(Appointment appointment);
    void scheduleReminder(Appointment appointment, LocalDateTime time);
}
