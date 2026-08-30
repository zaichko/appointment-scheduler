package com.zaichko.scheduler.repository;

import com.zaichko.scheduler.entity.Doctor;
import com.zaichko.scheduler.entity.Speciality;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Optional<Doctor> findByUserId(Long userId);

    boolean existsBySpecialitiesId(Long specialityId);

    void deleteByUserId(Long userId);
}
