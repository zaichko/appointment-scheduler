package com.zaichko.scheduler.repository;

import com.zaichko.scheduler.entity.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {
}
