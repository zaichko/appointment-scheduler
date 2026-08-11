package com.zaichko.scheduler.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "specialities")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Speciality {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(unique = true, length = 5)
    private String code;

    private String description;

    @Column(nullable = false)
    private boolean active;

    public Speciality(
            String name,
            String code,
            String description,
            boolean active
    ){
        this.name = name;
        this.code = code;
        this.description = description;
        this.active = active;
    }
}
