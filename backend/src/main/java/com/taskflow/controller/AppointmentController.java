package com.taskflow.controller;

import com.taskflow.model.Appointment;
import com.taskflow.model.User;
import com.taskflow.security.JwtUtils;
import com.taskflow.service.AppointmentService;
import com.taskflow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/appointments")
@CrossOrigin(origins = "http://localhost:3000")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    //Add appointment
    @PostMapping
    public String addAppointment(@RequestHeader("Authorization") String tokenHeader,
                                 @RequestBody Appointment appointment) {
        String token = tokenHeader.replace("Bearer ", "");
        String email = jwtUtils.extractEmail(token);

        appointmentService.addAppointment(appointment, email);
        return "✅ Appointment added for " + email;
    }

    //List the appointmets (USER: only his ; ADMIN: all the appointments)
    @GetMapping
    public ResponseEntity<List<Appointment>> getAppointments(@RequestHeader("Authorization") String tokenHeader) {
        String token = tokenHeader.replace("Bearer ", "");
        String email = jwtUtils.extractEmail(token);
        User user = userService.findByEmail(email);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
        }

        List<Appointment> result = appointmentService.getAppointmentsForRole(user.getEmail(), user.getRole());
        return ResponseEntity.ok(result);
    }

    //Update appointment
    @PutMapping("/{id}")
    public ResponseEntity<?> updateAppointment(
            @RequestHeader("Authorization") String tokenHeader,
            @PathVariable String id,
            @RequestBody Appointment appointment) {

        try {
            String token = tokenHeader.replace("Bearer ", "");
            String email = jwtUtils.extractEmail(token);
            User user = userService.findByEmail(email);

            if (appointment.getDateTime() == null) {
                return ResponseEntity.badRequest().body(Map.of("error", "Missing 'dateTime' field"));
            }

            appointmentService.updateAppointment(id, appointment.getDateTime(), user.getEmail(), user.getRole());
            return ResponseEntity.ok(Map.of(
                    "message", "Appointment " + id + " updated successfully",
                    "updatedBy", email
            ));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAppointment(
            @RequestHeader("Authorization") String tokenHeader,
            @PathVariable String id) {

        try {
            String token = tokenHeader.replace("Bearer ", "");
            String email = jwtUtils.extractEmail(token);
            User user = userService.findByEmail(email);

            appointmentService.deleteAppointment(id, email, user.getRole());
            return ResponseEntity.ok(Map.of(
                    "message", "🗑️ Appointment " + id + " deleted successfully",
                    "deletedBy", email
            ));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }
}


