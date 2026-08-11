package com.zaichko.scheduler.repository;

import com.zaichko.scheduler.entity.Speciality;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpecialityRepository extends JpaRepository<Speciality, Long> {
}
