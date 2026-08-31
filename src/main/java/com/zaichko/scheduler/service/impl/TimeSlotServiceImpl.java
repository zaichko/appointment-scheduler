package com.zaichko.scheduler.service.impl;

import com.zaichko.scheduler.dto.request.CreateTimeSlotRequest;
import com.zaichko.scheduler.dto.request.UpdateTimeSlotRequest;
import com.zaichko.scheduler.dto.response.TimeSlotResponse;
import com.zaichko.scheduler.entity.Doctor;
import com.zaichko.scheduler.entity.TimeSlot;
import com.zaichko.scheduler.exception.BookedSlotException;
import com.zaichko.scheduler.exception.NotFoundException;
import com.zaichko.scheduler.exception.TimeConflictException;
import com.zaichko.scheduler.mapper.TimeSlotMapper;
import com.zaichko.scheduler.repository.DoctorRepository;
import com.zaichko.scheduler.repository.TimeSlotRepository;
import com.zaichko.scheduler.service.TimeSlotService;
import jakarta.validation.ValidationException;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TimeSlotServiceImpl implements TimeSlotService {
    private final TimeSlotRepository timeSlotRepository;
    private final TimeSlotMapper timeSlotMapper;
    private final DoctorRepository doctorRepository;

    @Override
    public List<TimeSlotResponse> getAllTimeSlots(){
        List<TimeSlot> timeSlots = timeSlotRepository.findAll();
        if (timeSlots.isEmpty()){
            return Collections.emptyList();
        }

        ArrayList<TimeSlotResponse> timeSlotResponses = new ArrayList<>();

        for (TimeSlot timeSlot : timeSlots){
            timeSlotResponses.add(timeSlotMapper.toResponse(timeSlot));
        }
        return timeSlotResponses;
    }

    @Override
    public TimeSlotResponse getTimeSlotById(Long id){
        TimeSlot timeSlot = timeSlotRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Time Slot not found."));

        return timeSlotMapper.toResponse(timeSlot);
    }

    @Override
    public List<TimeSlotResponse> getAvailableTimeSlots(Long doctorId, Long specialityId, LocalDate date){
        LocalDateTime dateStart = null;
        LocalDateTime dateEnd = null;

        if (date != null) {
            dateStart = date.atStartOfDay();
            dateEnd = date.plusDays(1).atStartOfDay();
        }

        List<TimeSlot> timeSlots = timeSlotRepository.findAvailableSlots(doctorId, specialityId, dateStart, dateEnd);
        if (timeSlots.isEmpty()){
            return Collections.emptyList();
        }

        ArrayList<TimeSlotResponse> timeSlotResponses = new ArrayList<>();

        for (TimeSlot timeSlot : timeSlots){
            timeSlotResponses.add(timeSlotMapper.toResponse(timeSlot));
        }

        return timeSlotResponses;
    }

    @Override
    public TimeSlotResponse createTimeSlot(CreateTimeSlotRequest request){
        Doctor doctor = doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new NotFoundException("Doctor not found."));

        if (request.getEndTime().isBefore(request.getStartTime())){
            throw new ValidationException("Start time must be before end time.");
        }

        if (timeSlotRepository.existsOverlappingSlot(request.getDoctorId(), request.getStartTime(), request.getEndTime())){
            throw new TimeConflictException("The requested time interval conflicts with an existing schedule.");
        }

        TimeSlot timeSlot = new TimeSlot(doctor, request.getStartTime(), request.getEndTime());
        timeSlotRepository.save(timeSlot);

        return timeSlotMapper.toResponse(timeSlot);
    }

    @Override
    public TimeSlotResponse changeTimeInterval(UpdateTimeSlotRequest request){
        TimeSlot timeSlot = timeSlotRepository.findById(request.getId())
                .orElseThrow(() -> new NotFoundException("Time slot not found."));

        if (timeSlot.isBooked()){
            throw new BookedSlotException("Time slot is booked, no time changes allowed.");
        }

        if (request.getEndTime().isBefore(request.getStartTime())){
            throw new ValidationException("Start time must be before end time.");
        }

        if (
                timeSlotRepository.existsOverlappingSlotExcept(
                        timeSlot.getId(),
                        timeSlot.getDoctor().getId(),
                        request.getStartTime(),
                        request.getEndTime())
        ) {
            throw new TimeConflictException("The requested time interval conflicts with an existing schedule.");
        }

        timeSlot.setStartTime(request.getStartTime());
        timeSlot.setEndTime(request.getEndTime());

        TimeSlot savedSlot = timeSlotRepository.save(timeSlot);

        return timeSlotMapper.toResponse(savedSlot);

    }

    @Override
    public void deleteById(Long id){
        TimeSlot timeSlot = timeSlotRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Time slot not found."));

        if (timeSlot.isBooked()){
            throw new BookedSlotException("Time slot is booked and cannot be deleted.");
        }

        timeSlotRepository.deleteById(id);
    }
}
