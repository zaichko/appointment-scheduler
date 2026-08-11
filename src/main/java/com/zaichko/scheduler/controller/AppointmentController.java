package com.zaichko.scheduler.controller;

import com.zaichko.scheduler.dto.request.CreateAppointmentRequest;
import com.zaichko.scheduler.dto.response.AppointmentResponse;
import com.zaichko.scheduler.entity.Appointment;
import com.zaichko.scheduler.service.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {
    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService){
        this.appointmentService = appointmentService;
    }

    @GetMapping
    public List<Appointment> getAllAppointments(){return appointmentService.getAllAppointments();}

}
