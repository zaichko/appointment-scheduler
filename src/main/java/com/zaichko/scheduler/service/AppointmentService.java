package com.zaichko.scheduler.service;

import com.zaichko.scheduler.dto.request.CreateAppointmentRequest;
import com.zaichko.scheduler.dto.response.AppointmentResponse;

import java.util.List;

public interface AppointmentService {
    List<AppointmentResponse> getAllAppointments();

    AppointmentResponse getAppointmentById(Long id);

    AppointmentResponse createAppointment(CreateAppointmentRequest request);

    AppointmentResponse cancelAppointment(Long id);

    AppointmentResponse markCompleted(Long id);

    AppointmentResponse markNoShow(Long id);
}
