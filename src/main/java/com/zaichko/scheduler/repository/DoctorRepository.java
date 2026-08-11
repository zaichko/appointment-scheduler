package com.zaichko.scheduler.repository;

import com.zaichko.scheduler.entity.Doctor;
import com.zaichko.scheduler.entity.Speciality;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    List<Doctor> findBySpecialitiesContaining(Speciality speciality);
}
