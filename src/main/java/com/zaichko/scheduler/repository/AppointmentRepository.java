package com.zaichko.scheduler.repository;

import com.zaichko.scheduler.entity.Appointment;
import com.zaichko.scheduler.enums.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    Optional<Appointment> findByTimeSlotId(Long timeSlotId);

    boolean existsByPatientIdAndStatusAndTimeSlotStartTimeLessThanAndTimeSlotEndTimeGreaterThan(
            Long patientId,
            AppointmentStatus status,
            LocalDateTime newStart,
            LocalDateTime newEnd
    );

    boolean existsByPatientId(Long patientId);

    @Query("""
            SELECT COUNT(a.id) > 0
            FROM appointments a
            JOIN time_slots t ON a.time_slot_id = t.id
            WHERE t.doctor_id = :doctorId
    """)
    boolean existsByDoctorId(@Param("doctorId") Long doctorId);
}
