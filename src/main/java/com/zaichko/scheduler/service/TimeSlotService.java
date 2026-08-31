package com.zaichko.scheduler.service;

import com.zaichko.scheduler.dto.request.CreateTimeSlotRequest;
import com.zaichko.scheduler.dto.request.UpdateTimeSlotRequest;
import com.zaichko.scheduler.dto.response.TimeSlotResponse;

import java.time.LocalDate;
import java.util.List;

public interface TimeSlotService {
        List<TimeSlotResponse> getAllTimeSlots();

        TimeSlotResponse getTimeSlotById(Long id);

        List<TimeSlotResponse> getAvailableTimeSlots(Long doctorId, Long specialityId, LocalDate date);

        TimeSlotResponse createTimeSlot(CreateTimeSlotRequest request);

        TimeSlotResponse changeTimeInterval(UpdateTimeSlotRequest request);

        void deleteById(Long id);
}
