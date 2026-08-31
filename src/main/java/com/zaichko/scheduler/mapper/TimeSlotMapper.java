package com.zaichko.scheduler.mapper;

import com.zaichko.scheduler.dto.response.TimeSlotResponse;
import com.zaichko.scheduler.entity.TimeSlot;
import org.springframework.stereotype.Component;

@Component
public class TimeSlotMapper {
    public TimeSlotResponse toResponse(TimeSlot timeSlot){
        String doctorFullName = String.format("%s %s", timeSlot.getDoctor().getUser().getFirstName(), timeSlot.getDoctor().getUser().getLastName()).trim();

        return new TimeSlotResponse(
                timeSlot.getId(),
                doctorFullName,
                timeSlot.getStartTime(),
                timeSlot.getEndTime(),
                timeSlot.isBooked()
        );
    }
}
