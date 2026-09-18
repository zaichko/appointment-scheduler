package com.zaichko.scheduler.controller;

import com.zaichko.scheduler.dto.request.TimeSlotRequest;
import com.zaichko.scheduler.dto.response.TimeSlotResponse;
import com.zaichko.scheduler.service.TimeSlotService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/time-slots")
public class TimeSlotController {
    private final TimeSlotService timeSlotService;

    @GetMapping
    public List<TimeSlotResponse> getAllSlots(){
        return timeSlotService.getAllTimeSlots();
    }

    @GetMapping("/{id}")
    public TimeSlotResponse getSlotById(@PathVariable @Positive Long id){
        return timeSlotService.getTimeSlotById(id);
    }

    @GetMapping("/available")
    public List<TimeSlotResponse> getAvailableSlots(
            @RequestParam(required = false) @Positive Long doctorId,
            @RequestParam(required = false) @Positive Long specialityId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
            ){
        return timeSlotService.getAvailableTimeSlots(doctorId, specialityId, date);
    }

    @PostMapping("/{doctorId}")
    public TimeSlotResponse createTimeSlot(@PathVariable @Positive Long doctorId, @Valid @RequestBody TimeSlotRequest request){
        return timeSlotService.createTimeSlot(doctorId, request);
    }

    @PostMapping("/{id}/change-time")
    public TimeSlotResponse changeTimeInterval(@PathVariable @Positive Long id,@Valid @RequestBody TimeSlotRequest request){
        return timeSlotService.changeTimeInterval(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteTimeSLot(@PathVariable @Positive Long id){
        timeSlotService.deleteTimeSlotById(id);
    }
}
