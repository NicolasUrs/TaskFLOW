package com.taskflow.storage;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.taskflow.model.User;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserStorage {

    private static final String FILE_PATH = "src/main/resources/data/users.json";
    private final Gson gson;

    public UserStorage() {
        gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public void saveUsers(List<User> users) {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(users, writer);
            System.out.println("Users saved successfully to " + FILE_PATH);
        } catch (IOException e) {
            System.err.println("Error saving users: " + e.getMessage());
        }
    }

    public List<User> loadUsers() {
        try (FileReader reader = new FileReader(FILE_PATH)) {
            Type listType = new TypeToken<List<User>>() {}.getType();
            List<User> users = gson.fromJson(reader, listType);
            return users != null ? users : new ArrayList<>();
        } catch (IOException e) {
            System.err.println("No users file found, creating a new one.");
            return new ArrayList<>();
        }
    }
}