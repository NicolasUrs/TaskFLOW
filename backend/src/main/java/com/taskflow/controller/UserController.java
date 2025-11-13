package com.taskflow.controller;

import com.taskflow.model.User;
import com.taskflow.service.UserService;
import com.taskflow.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    //Returns all users (only for admin!)
    @GetMapping
    public List<User> getAllUsers(@RequestHeader("Authorization") String tokenHeader) {
        String token = tokenHeader.replace("Bearer ", "");

        if (!jwtUtils.isTokenValid(token)) {
            throw new RuntimeException("Invalid or expired token!");
        }

        String email = jwtUtils.extractEmail(token);
        User user = userService.findByEmail(email);

        if (user == null || !"ADMIN".equalsIgnoreCase(user.getRole())) {
            throw new RuntimeException("Access denied: ADMIN role required!");
        }

        return userService.getAllUsers();
    }

    //Deletes user by email
    @DeleteMapping("/{email}")
    public ResponseEntity<?> deleteUser(@RequestHeader("Authorization") String tokenHeader,
                                        @PathVariable String email) {
        try {
            String token = tokenHeader.replace("Bearer ", "");
            String requesterEmail = jwtUtils.extractEmail(token);
            User requester = userService.findByEmail(requesterEmail);

            userService.deleteUser(email, requesterEmail, requester.getRole());

            return ResponseEntity.ok(Map.of(
                    "message", "🗑️ User " + email + " deleted successfully by " + requesterEmail
            ));
        } catch (SecurityException e) {
            return ResponseEntity.status(403).body(Map.of(
                    "error", "Access Denied",
                    "message", e.getMessage()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                    "error", "Server Error",
                    "message", e.getMessage()
            ));
        }
    }

}

