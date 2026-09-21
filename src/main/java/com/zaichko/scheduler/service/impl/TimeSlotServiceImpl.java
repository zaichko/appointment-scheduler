package com.zaichko.scheduler.service.impl;

import com.zaichko.scheduler.dto.request.TimeSlotRequest;
import com.zaichko.scheduler.dto.response.TimeSlotResponse;
import com.zaichko.scheduler.entity.Doctor;
import com.zaichko.scheduler.entity.TimeSlot;
import com.zaichko.scheduler.enums.AppointmentStatus;
import com.zaichko.scheduler.exception.ConflictException;
import com.zaichko.scheduler.exception.NotFoundException;
import com.zaichko.scheduler.mapper.TimeSlotMapper;
import com.zaichko.scheduler.repository.AppointmentRepository;
import com.zaichko.scheduler.repository.DoctorRepository;
import com.zaichko.scheduler.repository.TimeSlotRepository;
import com.zaichko.scheduler.service.TimeSlotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
@Transactional
public class TimeSlotServiceImpl implements TimeSlotService {
    private static final ZoneId CLINIC_ZONE = ZoneId.of("Asia/Almaty");

    private final TimeSlotRepository timeSlotRepository;
    private final TimeSlotMapper timeSlotMapper;
    private final DoctorRepository doctorRepository;
    private final AppointmentRepository appointmentRepository;

    @Override
    @Transactional(readOnly = true)
    public boolean isSlotAvailable(Long id){
        if (!timeSlotRepository.existsById(id)){
            return false;
        }

        return !appointmentRepository.existsByTimeSlotIdAndStatusNot(id, AppointmentStatus.CANCELED);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TimeSlotResponse> getAllTimeSlots(){
        List<TimeSlot> timeSlots = timeSlotRepository.findAll();
        Set<Long> bookedSlotIds = new HashSet<>(appointmentRepository.findBookedTimeSlotIds(AppointmentStatus.CANCELED));

        return timeSlots.stream()
                .map(timeSlot -> timeSlotMapper.toResponse(timeSlot, !bookedSlotIds.contains(timeSlot.getId())))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public TimeSlotResponse getTimeSlotById(Long id){
        TimeSlot timeSlot = timeSlotRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Time Slot not found."));
        return timeSlotMapper.toResponse(timeSlot, isSlotAvailable(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TimeSlotResponse> getAvailableTimeSlots(Long doctorId, Long specialityId, LocalDate date){
        if (date == null) {
            throw new IllegalArgumentException("Date is required.");
        }
        OffsetDateTime dateStart = date.atStartOfDay(CLINIC_ZONE).toOffsetDateTime();
        OffsetDateTime dateEnd = date.plusDays(1).atStartOfDay(CLINIC_ZONE).toOffsetDateTime();

        List<TimeSlot> timeSlots = timeSlotRepository.findAvailableSlots(doctorId, specialityId, dateStart, dateEnd);

        return timeSlots.stream()
                .map(timeSlot -> timeSlotMapper.toResponse(timeSlot, true))
                .toList();
    }

    @Override
    public TimeSlotResponse createTimeSlot(Long doctorId, TimeSlotRequest request){
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new NotFoundException("Doctor not found."));

        if (timeSlotRepository.existsOverlappingSlot(
                doctorId, request.startTime(), request.endTime())){
            throw new ConflictException("The requested time interval conflicts with an existing schedule.");
        }

        TimeSlot timeSlot = new TimeSlot(doctor, request.startTime(), request.endTime());
        TimeSlot savedSlot = timeSlotRepository.save(timeSlot);

        return timeSlotMapper.toResponse(timeSlot, isSlotAvailable(savedSlot.getId()));
    }

    @Override
    public TimeSlotResponse changeTimeInterval(Long id, TimeSlotRequest request){
        TimeSlot timeSlot = timeSlotRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Time slot not found."));

        if (!isSlotAvailable(id)){
            throw new ConflictException("Time slot is booked, no time changes allowed.");
        }

        if (
                timeSlotRepository.existsOverlappingSlotExcept(
                        timeSlot.getId(),
                        timeSlot.getDoctor().getId(),
                        request.startTime(),
                        request.endTime())
        ) {
            throw new ConflictException("The requested time interval conflicts with an existing schedule.");
        }

        timeSlot.setStartTime(request.startTime());
        timeSlot.setEndTime(request.endTime());
        TimeSlot savedSlot = timeSlotRepository.save(timeSlot);

        return timeSlotMapper.toResponse(savedSlot, isSlotAvailable(id));

    }

    @Override
    public void deleteTimeSlotById(Long id){
        if (!timeSlotRepository.existsById(id)){
            throw new NotFoundException("Time slot not found.");
        }
        if (!isSlotAvailable(id) || appointmentRepository.existsByTimeSlotId(id)){
            throw new ConflictException("Time slot cannot be deleted.");
        }

        timeSlotRepository.deleteById(id);
    }
}