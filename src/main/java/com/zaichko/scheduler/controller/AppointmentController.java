package com.zaichko.scheduler.controller;

import com.zaichko.scheduler.dto.request.AppointmentRequest;
import com.zaichko.scheduler.dto.response.AppointmentResponse;
import com.zaichko.scheduler.service.impl.AppointmentServiceImpl;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentController {
    private final AppointmentServiceImpl appointmentService;

    @GetMapping
    public List<AppointmentResponse> getAllAppointments(){
        return appointmentService.getAllAppointments();
    }

    @GetMapping("/{id}")
    public AppointmentResponse getAppointmentById(@PathVariable @Positive Long id){
        return appointmentService.getAppointmentById(id);
    }

    @PostMapping("/book")
    public AppointmentResponse bookAppointment(@Valid @RequestBody AppointmentRequest request){
        return appointmentService.createAppointment(request);
    }

    @PostMapping("/cancel")
    public AppointmentResponse cancelAppointment(@Valid @RequestBody AppointmentRequest request){
        return appointmentService.cancelAppointment(request);
    }

    @PostMapping("/complete")
    public AppointmentResponse markAppointmentCompleted(@Valid @RequestBody AppointmentRequest request){
        return appointmentService.markCompleted(request);
    }

    @PostMapping("/hide")
    public AppointmentResponse hideAppointment(@Valid @RequestBody AppointmentRequest request){
        return appointmentService.hideAppointment(request);
    }
}
