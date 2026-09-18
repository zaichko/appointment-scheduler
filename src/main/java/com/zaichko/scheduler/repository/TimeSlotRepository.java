package com.zaichko.scheduler.repository;

import com.zaichko.scheduler.entity.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {
    @Query("""
        SELECT CASE WHEN COUNT(ts) > 0 THEN TRUE ELSE FALSE END
        FROM TimeSlot ts
        WHERE ts.doctor.id = :doctorId
          AND ts.startTime < :newEndTime
          AND ts.endTime > :newStartTime
    """)
    boolean existsOverlappingSlot(
            @Param("doctorId") Long doctorId,
            @Param("newStartTime") LocalDateTime newStartTime,
            @Param("newEndTime") LocalDateTime newEndTime
    );

    @Query("""
        SELECT CASE WHEN COUNT(ts) > 0 THEN TRUE ELSE FALSE END
        FROM TimeSlot ts
        WHERE ts.doctor.id = :doctorId
          AND ts.id <> :timeSlotId
          AND ts.startTime < :newEndTime
          AND ts.endTime > :newStartTime
    """)
    boolean existsOverlappingSlotExcept(
            @Param("timeSlotId") Long timeSlotId,
            @Param("doctorId") Long doctorId,
            @Param("newStartTime") LocalDateTime newStartTime,
            @Param("newEndTime") LocalDateTime newEndTime
    );

    @Query("""
        SELECT DISTINCT ts
        FROM TimeSlot ts
        JOIN ts.doctor d
        LEFT JOIN d.specialities s
        LEFT JOIN Appointment a ON a.timeSlot = ts AND a.status != 'CANCELED'
        WHERE a.id IS NULL
          AND (:doctorId IS NULL OR d.id = :doctorId)
          AND (:specialityId IS NULL OR s.id = :specialityId)
          AND (:dateStart IS NULL OR ts.startTime >= :dateStart)
          AND (:dateEnd IS NULL OR ts.startTime < :dateEnd)
        ORDER BY ts.startTime
    """)
    List<TimeSlot> findAvailableSlots(
            @Param("doctorId") Long doctorId,
            @Param("specialityId") Long specialityId,
            @Param("dateStart") LocalDateTime dateStart,
            @Param("dateEnd") LocalDateTime dateEnd
    );

    boolean existsByDoctorId(Long doctorId);
}
