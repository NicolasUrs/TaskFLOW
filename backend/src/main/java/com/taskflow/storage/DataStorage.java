package com.taskflow.storage;

import com.taskflow.model.Appointment;
import java.util.List;

public interface DataStorage {
    void saveAppointments(List<Appointment> appointments);
    List<Appointment> loadAppointments();
}
