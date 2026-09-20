package com.zaichko.scheduler.service.impl;

import com.zaichko.scheduler.dto.request.CreateAppointmentRequest;
import com.zaichko.scheduler.dto.response.AppointmentResponse;
import com.zaichko.scheduler.entity.Appointment;
import com.zaichko.scheduler.entity.TimeSlot;
import com.zaichko.scheduler.entity.User;
import com.zaichko.scheduler.enums.AppointmentStatus;
import com.zaichko.scheduler.enums.Role;
import com.zaichko.scheduler.exception.*;
import com.zaichko.scheduler.mapper.AppointmentMapper;
import com.zaichko.scheduler.repository.AppointmentRepository;
import com.zaichko.scheduler.repository.TimeSlotRepository;
import com.zaichko.scheduler.repository.UserRepository;
import com.zaichko.scheduler.service.AppointmentService;
import com.zaichko.scheduler.service.TimeSlotService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;
    private final TimeSlotRepository timeSlotRepository;
    private final UserRepository userRepository;
    private final TimeSlotService timeSlotService;

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentResponse> getAllAppointments() {
        List<Appointment> appointments = appointmentRepository.findAll();

        return appointments.stream().map(appointmentMapper :: toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public AppointmentResponse getAppointmentById(Long id){
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Appointment not found."));
        return appointmentMapper.toResponse(appointment);
    }

    @Override
    public AppointmentResponse createAppointment(CreateAppointmentRequest request){
        User patient = userRepository.findByIdForUpdate(request.patientId())
                .orElseThrow(() -> new NotFoundException("User not found."));
        if (patient.getRole() != Role.PATIENT){
            throw new AccessDeniedException("Only patients are allowed to book appointment slots.");
        }

        TimeSlot timeSlot = timeSlotRepository.findById(request.timeSlotId())
                .orElseThrow(() -> new NotFoundException("Time Slot not found."));

        if (!timeSlotService.isSlotAvailable(timeSlot.getId())){
            throw new ConflictException("Time slot already booked.");
        }
        if (
                appointmentRepository.hasOverlappingAppointment(
                        patient.getId(),
                        timeSlot.getStartTime(),
                        timeSlot.getEndTime()
                )
        ){
            throw new ConflictException("The requested time interval conflicts with an existing schedule.");
        }

        Appointment appointmentEntity = new Appointment(patient, timeSlot);
        Appointment savedAppointment = appointmentRepository.save(appointmentEntity);

        return appointmentMapper.toResponse(savedAppointment);
    }

    @Override
    public AppointmentResponse cancelAppointment(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Appointment not found."));

        if (appointment.getStatus() != AppointmentStatus.SCHEDULED)
            throw new ConflictException("Appointment cannot be canceled.");

        appointment.setStatus(AppointmentStatus.CANCELED);
        Appointment savedAppointment = appointmentRepository.save(appointment);

        return appointmentMapper.toResponse(savedAppointment);
    }

    @Override
    public AppointmentResponse markCompleted(Long id){
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Appointment not found."));

        if (appointment.getStatus() != AppointmentStatus.SCHEDULED){
            throw new ConflictException("Appointment is not scheduled.");
        }

        appointment.setStatus(AppointmentStatus.COMPLETED);
        Appointment savedAppointment = appointmentRepository.save(appointment);

        return appointmentMapper.toResponse(savedAppointment);
    }

    @Override
    public AppointmentResponse markNoShow(Long id){
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Appointment not found."));

        if (appointment.getStatus() != AppointmentStatus.SCHEDULED){
            throw new ConflictException("Appointment is not scheduled.");
        }

        appointment.setStatus(AppointmentStatus.NO_SHOW);
        Appointment savedAppointment = appointmentRepository.save(appointment);

        return appointmentMapper.toResponse(savedAppointment);
    }
}