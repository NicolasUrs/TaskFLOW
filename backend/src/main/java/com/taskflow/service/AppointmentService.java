package com.taskflow.service;

import com.taskflow.model.Appointment;
import com.taskflow.model.User;
import com.taskflow.storage.DataStorage;
import com.taskflow.exceptions.InvalidAppointmentException;
import com.taskflow.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class AppointmentService {

    private final DataStorage storage;
    private final List<Appointment> appointments;

    public AppointmentService(DataStorage storage) {
        this.storage = storage;
        this.appointments = new ArrayList<>(storage.loadAppointments());
    }

    //Add appointment
    public void addAppointment(Appointment appointment, String userEmail) {
        if (appointment == null) throw new InvalidAppointmentException("Appointment object cannot be null!");
        if (appointment.getDateTime() == null) throw new InvalidAppointmentException("Appointment must have a valid dateTime!");
        if (appointment.getDateTime().isBefore(LocalDateTime.now()))
            throw new InvalidAppointmentException("Appointment dateTime must be in the future!");

        appointment.setId(UUID.randomUUID().toString());
        appointment.setCreatedBy(userEmail);
        appointments.add(appointment);

        storage.saveAppointments(appointments);
        System.out.println("Added appointment for " + userEmail + " with ID " + appointment.getId());
    }

    //Delete self appointment
    public void deleteAppointment(String id, String email, String role) {
        List<Appointment> all = readAppointments();
        boolean removed = all.removeIf(a ->
                a.getId().equals(id) &&
                        (email.equalsIgnoreCase(a.getUser().getEmail()) || "ADMIN".equals(role))
        );

        if (!removed) {
            throw new RuntimeException("Cannot delete appointment! (not found or no permission)");
        }

        writeAppointments(all);
        System.out.println("Deleted appointment with ID " + id + " by user " + email);
    }

    //Delete all appointments when user deleted
    public void deleteAppointmentsByUser(String email) {
        appointments.removeIf(a -> email.equalsIgnoreCase(a.getCreatedBy()));
        storage.saveAppointments(appointments);
        System.out.println("Deleted all appointments for user " + email);
    }

    //Modifies (PUT)
    public void updateAppointment(String id, LocalDateTime newDateTime, String email, String role) {
        List<Appointment> all = readAppointments();

        for (Appointment a : all) {
            if (a.getId().equals(id)) {
                if (!email.equalsIgnoreCase(a.getUser().getEmail()) && !"ADMIN".equals(role)) {
                    throw new RuntimeException("Permission denied!");
                }

                a.setDateTime(newDateTime);
                writeAppointments(all);
                System.out.println("Appointment updated by " + email + " (ID: " + id + ")");
                return;
            }
        }

        throw new RuntimeException("Appointment not found: " + id);
    }

    public List<Appointment> getAllAppointments() {
        return Collections.unmodifiableList(appointments);
    }

    public List<Appointment> getAppointmentsForRole(String email, String role) {
        List<Appointment> all = readAppointments();
        if ("ADMIN".equals(role)) {
            return all;
        }
        return all.stream()
                .filter(a -> a.getUser().getEmail().equalsIgnoreCase(email))
                .toList();
    }

    private List<Appointment> readAppointments() {
        return new ArrayList<>(appointments);
    }

    public void reloadFromFile() {
        appointments.clear();
        appointments.addAll(storage.loadAppointments());
        System.out.println("Reloaded appointments from file.");
    }

    private void writeAppointments(List<Appointment> updated) {
        appointments.clear();
        appointments.addAll(updated);
        storage.saveAppointments(appointments);
    }

}
