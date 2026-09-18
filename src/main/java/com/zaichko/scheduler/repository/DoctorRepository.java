package com.zaichko.scheduler.repository;

import com.zaichko.scheduler.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    boolean existsBySpecialitiesId(Long specialityId);
}
