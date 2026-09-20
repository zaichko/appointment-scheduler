package com.zaichko.scheduler.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "doctors")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer experience;

    @Column(columnDefinition = "TEXT")
    private String bio;

    @ManyToMany
    @JoinTable(
            name = "doctor_speciality",
            joinColumns = @JoinColumn(name = "doctor_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "speciality_id", nullable = false)
    )
    @BatchSize(size = 20)
    private Set<Speciality> specialities;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private User user;

    public Doctor(
            Integer experience,
            String bio,
            Set<Speciality> specialities,
            User user
    ){
        this.experience = experience;
        this.bio = bio;
        this.specialities = specialities;
        this.user = user;
    }
}
