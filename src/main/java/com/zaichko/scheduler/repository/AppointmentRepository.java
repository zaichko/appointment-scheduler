package com.zaichko.scheduler.repository;

import com.zaichko.scheduler.entity.Appointment;
import com.zaichko.scheduler.enums.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("""
            SELECT CASE WHEN COUNT(a) > 0 THEN TRUE ELSE FALSE END
            FROM Appointment a
            WHERE a.patient.id = :patientId
                AND a.status = 'SCHEDULED'
                AND a.timeSlot.startTime < :newEndTime
                AND a.timeSlot.endTime > :newStartTime
    """)
    boolean hasOverlappingAppointment(
            @Param("patientId") Long patientId,
            @Param("newStartTime") OffsetDateTime newStartTime,
            @Param("newEndTime") OffsetDateTime newEndTime
    );

    boolean existsByPatientId(Long patientId);

    boolean existsByTimeSlotId(Long timeSlotId);

    boolean existsByTimeSlotDoctorId(Long doctorId);

    boolean existsByTimeSlotIdAndStatusNot(Long timeSlotId, AppointmentStatus status);
}
