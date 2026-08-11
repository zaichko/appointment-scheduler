package com.zaichko.scheduler.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "time_slots")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TimeSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @Column(name = "start_time",nullable = false)
    private Instant startTime;

    @Column(name = "end_time", nullable = false)
    private Instant endTime;

    @Column(name = "is_booked", nullable = false)
    private boolean isBooked;

    @Version
    private Integer version;

    public TimeSlot(
            Doctor doctor,
            Instant startTime,
            Instant endTime
    ){
        this.doctor = doctor;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isBooked = false;
    }

}
