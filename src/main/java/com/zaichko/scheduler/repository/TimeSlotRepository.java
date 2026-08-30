package com.zaichko.scheduler.repository;

import com.zaichko.scheduler.entity.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {
    @Query("""
    SELECT COUNT(ts) > 0
    FROM TimeSlot ts
    WHERE ts.doctor.id = :doctorId
      AND ts.startTime < :newEndTime
      AND ts.endTime > :newStartTime
""")
    boolean existsOverlappingSlot(
            Long doctorId,
            LocalDateTime newStartTime,
            LocalDateTime newEndTime
    );

    @Query("""
    SELECT COUNT(ts) > 0
    FROM TimeSlot ts
    WHERE ts.doctor.id = :doctorId
      AND ts.id <> :timeSlotId
      AND ts.startTime < :newEndTime
      AND ts.endTime > :newStartTime
""")
    boolean existsOverlappingSlotExcept(
            Long timeSlotId,
            Long doctorId,
            LocalDateTime newStartTime,
            LocalDateTime newEndTime
    );

    @Query("""
    SELECT ts
    FROM TimeSlot ts
    JOIN ts.doctor d
    JOIN d.specialities s
    WHERE ts.isBooked = false
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
