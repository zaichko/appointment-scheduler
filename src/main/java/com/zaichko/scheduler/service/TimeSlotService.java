package com.zaichko.scheduler.service;

import com.zaichko.scheduler.entity.TimeSlot;
import com.zaichko.scheduler.repository.TimeSlotRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimeSlotService {
    private final TimeSlotRepository timeSlotRepository;

    public TimeSlotService(TimeSlotRepository timeSlotRepository){
        this.timeSlotRepository = timeSlotRepository;
    }

    public List<TimeSlot> getAllTimeSlots(){
        return timeSlotRepository.findAll();
    }
}
