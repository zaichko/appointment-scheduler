package com.zaichko.scheduler.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
@Table(name = "time_slots", indexes = {
        @Index(name = "idx_time_slot_doctor_start_time", columnList = "doctor_id, start_time")
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TimeSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @Column(name = "start_time",nullable = false)
    private OffsetDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private OffsetDateTime endTime;

    @Version
    private Integer version = 0;

    public TimeSlot(
            Doctor doctor,
            OffsetDateTime startTime,
            OffsetDateTime endTime
    ){
        this.doctor = doctor;
        this.startTime = startTime;
        this.endTime = endTime;
    }

}
