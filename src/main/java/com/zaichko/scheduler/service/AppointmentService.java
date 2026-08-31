package com.zaichko.scheduler.service;

import com.zaichko.scheduler.dto.request.AppointmentRequest;
import com.zaichko.scheduler.dto.response.AppointmentResponse;

import java.util.List;

public interface AppointmentService {
    List<AppointmentResponse> getAllAppointments();

    AppointmentResponse getAppointmentById(Long id);

    AppointmentResponse createAppointment(AppointmentRequest request);

    AppointmentResponse cancelAppointment(AppointmentRequest request);

    AppointmentResponse markCompleted(AppointmentRequest request);

    AppointmentResponse hideAppointment(AppointmentRequest request);
}
