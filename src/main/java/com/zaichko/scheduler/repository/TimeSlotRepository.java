package com.zaichko.scheduler.repository;

import com.zaichko.scheduler.entity.TimeSlot;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;
import java.util.List;

public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {
    @Override
    @EntityGraph(attributePaths = {"doctor", "doctor.user"})
    @NonNull
    List<TimeSlot> findAll();

    @Query("""
        SELECT CASE WHEN COUNT(ts) > 0 THEN TRUE ELSE FALSE END
        FROM TimeSlot ts
        WHERE ts.doctor.id = :doctorId
          AND ts.startTime < :newEndTime
          AND ts.endTime > :newStartTime
    """)
    boolean existsOverlappingSlot(
            @Param("doctorId") Long doctorId,
            @Param("newStartTime") OffsetDateTime newStartTime,
            @Param("newEndTime") OffsetDateTime newEndTime
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
            @Param("newStartTime") OffsetDateTime newStartTime,
            @Param("newEndTime") OffsetDateTime newEndTime
    );

    @Query("""
        SELECT DISTINCT ts
        FROM TimeSlot ts
        JOIN FETCH ts.doctor d
        JOIN FETCH d.user
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
            @Param("dateStart") OffsetDateTime dateStart,
            @Param("dateEnd") OffsetDateTime dateEnd
    );

    boolean existsByDoctorId(Long doctorId);
}
