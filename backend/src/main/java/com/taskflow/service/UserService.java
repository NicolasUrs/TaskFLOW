package com.taskflow.service;

import com.taskflow.exceptions.NotFoundException;
import com.taskflow.model.User;
import com.taskflow.security.JwtUtils;
import com.taskflow.storage.UserStorage;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserStorage userStorage;

    private List<User> users;

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    //AppointmentService pair
    @Autowired
    private AppointmentService appointmentService;

    @PostConstruct
    public void init() {
        this.users = userStorage.loadUsers();
    }

    //Register
    public User register(User user) {
        if (users.stream().anyMatch(u -> u.getEmail().equalsIgnoreCase(user.getEmail()))) {
            throw new RuntimeException("User already exists with this email.");
        }

        user.setId(users.size() + 1);
        user.setPassword(passwordEncoder.encode(user.getPassword())); // parolele se criptează
        user.setRole(user.getRole() == null ? "USER" : user.getRole());
        users.add(user);
        userStorage.saveUsers(users);

        System.out.println("✅ Registered user: " + user.getEmail());
        return user;
    }

    //Login
    public String login(String email, String password) {
        User existing = users.stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("User not found."));

        if (!passwordEncoder.matches(password, existing.getPassword())) {
            throw new RuntimeException("Invalid password.");
        }

        System.out.println("🔑 Login successful for " + email);
        return jwtUtils.generateToken(existing.getEmail());
    }

    //Delete user (self + admin)
    public boolean deleteUser(String email, String requestedBy, String role) {
        Optional<User> target = users.stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst();

        if (target.isEmpty()) {
            throw new NotFoundException("User with email " + email + " not found.");
        }

        //Permision verifier
        if (!requestedBy.equalsIgnoreCase(email) && !"ADMIN".equalsIgnoreCase(role)) {
            throw new SecurityException("You are not allowed to delete this user.");
        }

        users.remove(target.get());
        userStorage.saveUsers(users);

        //Delete all Appointment by default when user is deleted
        appointmentService.deleteAppointmentsByUser(email);

        System.out.println("🗑️ User " + email + " deleted by " + requestedBy);
        return true;
    }

    //Search user by email
    public User findByEmail(String email) {
        return users.stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }

    //Returns all users
    public List<User> getAllUsers() {
        return users;
    }

    //Reload from file (if manual modification)
    //Note: Rerun the backend for modifying
    public void reloadfromFile() {
        this.users = userStorage.loadUsers();
        System.out.println("🔄 Users reloaded from file.");
    }
}
