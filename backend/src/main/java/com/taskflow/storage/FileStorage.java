package com.taskflow.storage;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.taskflow.model.Appointment;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class FileStorage implements DataStorage {

    private static final String FILE_PATH = "src/main/resources/data/appointments.json";
    private final Gson gson;

    public FileStorage() {
        gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class,
                        (JsonSerializer<LocalDateTime>) (src, typeOfSrc, context) ->
                                new JsonPrimitive(src.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
                .registerTypeAdapter(LocalDateTime.class,
                        (JsonDeserializer<LocalDateTime>) (json, typeOfT, context) ->
                                LocalDateTime.parse(json.getAsString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .setPrettyPrinting()
                .create();
    }

    @Override
    public void saveAppointments(List<Appointment> appointments) {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(appointments, writer);
            System.out.println("✅ Appointments saved successfully to " + FILE_PATH);
        } catch (IOException e) {
            System.err.println("❌ Error saving appointments: " + e.getMessage());
        }
    }

    @Override
    public List<Appointment> loadAppointments() {
        try (FileReader reader = new FileReader(FILE_PATH)) {
            Type listType = new TypeToken<List<Appointment>>() {}.getType();
            List<Appointment> appointments = gson.fromJson(reader, listType);
            return appointments != null ? appointments : new ArrayList<>();
        } catch (IOException e) {
            System.err.println("⚠️ No appointments found, returning empty list.");
            return new ArrayList<>();
        }
    }
}
