package com.zaichko.scheduler.entity;

import com.zaichko.scheduler.enums.AppointmentStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "appointments")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private User patient;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "time_slot_id", unique = true, nullable = false)
    private TimeSlot timeSlot;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AppointmentStatus status;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Instant createdAt;

    public Appointment(
            User patient,
            TimeSlot timeSlot
    ){
        this.patient = patient;
        this.timeSlot = timeSlot;
        this.status = AppointmentStatus.SCHEDULED;
    }

}
