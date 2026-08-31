package com.zaichko.scheduler.service.impl;

import com.zaichko.scheduler.dto.request.AppointmentRequest;
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
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;
    private final TimeSlotRepository timeSlotRepository;
    private final UserRepository userRepository;

    @Override
    public List<AppointmentResponse> getAllAppointments() {
        List<Appointment> appointments = appointmentRepository.findAll();
        if (appointments.isEmpty()){
            return Collections.emptyList();
        }

        ArrayList<AppointmentResponse> appointmentResponses = new ArrayList<>();

        for (Appointment appointment : appointments){
            appointmentResponses.add(appointmentMapper.toResponse(appointment));
        }

        return appointmentResponses;
    }

    @Override
    public AppointmentResponse getAppointmentById(Long id){
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Appointment not found."));
        return appointmentMapper.toResponse(appointment);
    }

    @Override
    @Transactional
    public AppointmentResponse createAppointment(AppointmentRequest request){
        User patient = userRepository.findById(request.getPatientId())
                .orElseThrow(() -> new NotFoundException("User not found."));

        if (patient.getRole() != Role.PATIENT){
            throw new UnavailableActionException("The user's role must be PATIENT.");
        }

        TimeSlot timeSlot = timeSlotRepository.findById(request.getTimeSlotId())
                .orElseThrow(() -> new NotFoundException("Time Slot not found."));

        if (timeSlot.isBooked()){
            throw new BookedSlotException("Time Slot already booked.");
        }

        if (
                appointmentRepository.existsByPatientIdAndStatusAndTimeSlotStartTimeLessThanAndTimeSlotEndTimeGreaterThan(
                        patient.getId(),
                        AppointmentStatus.SCHEDULED,
                        timeSlot.getStartTime(),
                        timeSlot.getEndTime()
                )
        ){
            throw new TimeConflictException("The requested time interval conflicts with an existing schedule.");
        }

        timeSlot.setBooked(true);
        timeSlotRepository.save(timeSlot);

        Appointment appointmentEntity = new Appointment(patient, timeSlot);

        Appointment savedAppointment = appointmentRepository.save(appointmentEntity);

        return appointmentMapper.toResponse(savedAppointment);
    }

    @Override
    @Transactional
    public AppointmentResponse cancelAppointment(AppointmentRequest request) {
        Appointment appointment = appointmentRepository.findByTimeSlotId(request.getTimeSlotId())
                .orElseThrow(() -> new NotFoundException("Appointment not found."));

        if (appointment.getStatus() != AppointmentStatus.SCHEDULED)
            throw new CancelAppointmentException("Appointment cannot be canceled.");

        appointment.setStatus(AppointmentStatus.CANCELED);

        Appointment savedAppointment = appointmentRepository.save(appointment);

        TimeSlot timeSlot = timeSlotRepository.findById(appointment.getTimeSlot().getId())
                .orElseThrow(() -> new NotFoundException("Time Slot not found."));

        timeSlot.setBooked(false);
        timeSlotRepository.save(timeSlot);

        String doctorFullName = String.format("%s %s", savedAppointment.getTimeSlot().getDoctor().getUser().getFirstName(), savedAppointment.getTimeSlot().getDoctor().getUser().getLastName()).trim();

        return new AppointmentResponse(
                savedAppointment.getId(),
                savedAppointment.getPatient().getFirstName(),
                doctorFullName,
                savedAppointment.getTimeSlot().getStartTime(),
                savedAppointment.getTimeSlot().getEndTime(),
                savedAppointment.getStatus().name()
        );
    }

    @Override
    public AppointmentResponse markCompleted(AppointmentRequest request){
        Appointment appointment = appointmentRepository.findByTimeSlotId(request.getTimeSlotId())
                .orElseThrow(() -> new NotFoundException("Appointment not found."));

        appointment.setStatus(AppointmentStatus.COMPLETED);
        Appointment savedAppointment = appointmentRepository.save(appointment);

        return appointmentMapper.toResponse(savedAppointment);
    }

    @Override
    public AppointmentResponse hideAppointment(AppointmentRequest request){
        Appointment appointment = appointmentRepository.findByTimeSlotId(request.getTimeSlotId())
                .orElseThrow(() -> new NotFoundException("Appointment not found."));

        appointment.setStatus(AppointmentStatus.NO_SHOW);
        Appointment savedAppointment = appointmentRepository.save(appointment);

        return appointmentMapper.toResponse(savedAppointment);
    }
}
