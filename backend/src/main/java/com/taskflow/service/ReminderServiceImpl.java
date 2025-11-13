package com.taskflow.service;

import com.taskflow.model.Appointment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ReminderServiceImpl implements ReminderService {

    private final AppointmentService appointmentService;
    private final Set<String> reminded = ConcurrentHashMap.newKeySet();

    public ReminderServiceImpl(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @Override
    public void sendReminder(Appointment a) {
        System.out.println("🔔 Reminder: " + a.getUser().getName()
                + " are programare la " + a.getDateTime()
                + " (ID=" + a.getId() + ")");
    }

    @Override
    public void scheduleReminder(Appointment a, LocalDateTime time) {
        // pentru midterm e gol — doar simulăm reminder-ul cu job periodic
    }

    @Scheduled(cron = "0 * * * * *") // la fiecare minut
    public void runScheduler() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime in24h = now.plusHours(24);

        for (Appointment a : appointmentService.getAllAppointments()) {
            if (a.getDateTime() == null || a.getId() == null) continue;

            boolean eInUrmatoarele24h = a.getDateTime().isAfter(now) && a.getDateTime().isBefore(in24h);
            if (eInUrmatoarele24h && reminded.add(a.getId())) {
                sendReminder(a);
            }
        }
    }
}
