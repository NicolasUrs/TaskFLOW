package com.taskflow.repository;

import com.taskflow.model.User;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class UserRepository {

    private final Map<String, User> users = new HashMap<>(); // key = email

    public User save(User user) {
        users.put(user.getEmail(), user);
        return user;
    }

    public User findByEmail(String email) {
        return users.get(email);
    }

    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    public void deleteByEmail(String email) {
        users.remove(email);
    }
}
