package com.zaichko.scheduler.repository;

import com.zaichko.scheduler.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}
