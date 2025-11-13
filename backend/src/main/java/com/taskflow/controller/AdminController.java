package com.taskflow.controller;

import com.taskflow.service.UserService;
import com.taskflow.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping("/reload")
    public String reloadData() {
        userService.reloadfromFile();
        appointmentService.reloadFromFile();
        return "Data reloaded from files.";
    }
}
