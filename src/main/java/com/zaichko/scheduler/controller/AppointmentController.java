package com.zaichko.scheduler.controller;

import com.zaichko.scheduler.dto.request.CreateAppointmentRequest;
import com.zaichko.scheduler.dto.response.AppointmentResponse;
import com.zaichko.scheduler.service.AppointmentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentController {
    private final AppointmentService appointmentService;

    @GetMapping
    public List<AppointmentResponse> getAllAppointments(){
        return appointmentService.getAllAppointments();
    }

    @GetMapping("/{id}")
    public AppointmentResponse getAppointmentById(@PathVariable @Positive Long id){
        return appointmentService.getAppointmentById(id);
    }

    @PostMapping("/book")
    public AppointmentResponse bookAppointment(@Valid @RequestBody CreateAppointmentRequest request){
        return appointmentService.createAppointment(request);
    }

    @PatchMapping("/{id}/cancel")
    public AppointmentResponse cancelAppointment(@PathVariable @Positive Long id){
        return appointmentService.cancelAppointment(id);
    }

    @PatchMapping("/{id}/complete")
    public AppointmentResponse markAppointmentCompleted(@PathVariable @Positive Long id){
        return appointmentService.markCompleted(id);
    }

    @PatchMapping("/{id}/no-show")
    public AppointmentResponse markAppointmentNoShow(@PathVariable @Positive Long id){
        return appointmentService.markNoShow(id);
    }
}
