package com.taskflow.controller;

import com.taskflow.model.User;
import com.taskflow.service.UserService;
import com.taskflow.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    //REGISTER
    @PostMapping("/register")
    public String register(@RequestBody User user) {
        userService.register(user);
        return "✅ User registered successfully!";
    }

    //LOGIN
    @PostMapping("/login")
    public Map<String, String> login(@RequestBody User user) {
        String token = userService.login(user.getEmail(), user.getPassword());
        User existingUser = userService.findByEmail(user.getEmail());

        return Map.of(
                "token", token,
                "role", existingUser.getRole(),
                "email", existingUser.getEmail(),
                "message", "🔑 Login successful!"
        );
    }
}
