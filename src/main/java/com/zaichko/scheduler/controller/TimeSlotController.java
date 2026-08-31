package com.zaichko.scheduler.controller;

import com.zaichko.scheduler.dto.request.CreateTimeSlotRequest;
import com.zaichko.scheduler.dto.request.UpdateTimeSlotRequest;
import com.zaichko.scheduler.dto.response.TimeSlotResponse;
import com.zaichko.scheduler.service.impl.TimeSlotServiceImpl;
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
    private final TimeSlotServiceImpl timeSlotService;

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

    @PostMapping
    public TimeSlotResponse createTimeSlot(@Valid @RequestBody CreateTimeSlotRequest request){
        return timeSlotService.createTimeSlot(request);
    }

    @PostMapping("/change-time")
    public TimeSlotResponse changeTimeInterval(@Valid @RequestBody UpdateTimeSlotRequest request){
        return timeSlotService.changeTimeInterval(request);
    }

    @DeleteMapping("/{id}")
    public void deleteTimeSLot(@PathVariable @Positive Long id){
        timeSlotService.deleteById(id);
    }
}
