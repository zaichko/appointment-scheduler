package com.zaichko.scheduler.repository;

import com.zaichko.scheduler.entity.Appointment;
import com.zaichko.scheduler.enums.AppointmentStatus;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    @Override
    @EntityGraph(attributePaths = {"patient", "timeSlot", "timeSlot.doctor", "timeSlot.doctor.user"})
    @NonNull
    List<Appointment> findAll();

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

    @Query("""
            SELECT DISTINCT a.timeSlot.id
            FROM Appointment a
            WHERE a.status <> :excludedStatus
    """)
    List<Long> findBookedTimeSlotIds(@Param("excludedStatus") AppointmentStatus excludedStatus);

    boolean existsByPatientId(Long patientId);

    boolean existsByTimeSlotId(Long timeSlotId);

    boolean existsByTimeSlotDoctorId(Long doctorId);

    boolean existsByTimeSlotIdAndStatusNot(Long timeSlotId, AppointmentStatus status);
}
