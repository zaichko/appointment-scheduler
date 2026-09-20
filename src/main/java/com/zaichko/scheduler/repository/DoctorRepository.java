package com.zaichko.scheduler.repository;

import com.zaichko.scheduler.entity.Doctor;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    @Override
    @EntityGraph(attributePaths = {"user", "specialities"})
    @NonNull
    List<Doctor> findAll();

    boolean existsBySpecialitiesId(Long specialityId);
}
