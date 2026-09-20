package com.zaichko.scheduler.service;

import com.zaichko.scheduler.dto.request.TimeSlotRequest;
import com.zaichko.scheduler.dto.response.TimeSlotResponse;

import java.time.LocalDate;
import java.util.List;

public interface TimeSlotService {
     boolean isSlotAvailable(Long id);

     List<TimeSlotResponse> getAllTimeSlots();

     TimeSlotResponse getTimeSlotById(Long id);

     List<TimeSlotResponse> getAvailableTimeSlots(Long doctorId, Long specialityId, LocalDate date);

     TimeSlotResponse createTimeSlot(Long doctorId, TimeSlotRequest request);

     TimeSlotResponse changeTimeInterval(Long id, TimeSlotRequest request);

     void deleteTimeSlotById(Long id);
}