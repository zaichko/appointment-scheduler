package com.zaichko.scheduler.service;

import com.zaichko.scheduler.entity.Appointment;
import com.zaichko.scheduler.repository.AppointmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;

    public AppointmentService(AppointmentRepository appointmentRepository){
        this.appointmentRepository = appointmentRepository;
    }

    public List<Appointment> getAllAppointments(){
        return appointmentRepository.findAll();
    }
}
