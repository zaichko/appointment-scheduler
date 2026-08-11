package com.zaichko.scheduler.service;

import com.zaichko.scheduler.entity.Doctor;
import com.zaichko.scheduler.repository.DoctorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {
    private final DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository){
        this.doctorRepository = doctorRepository;
    }

    public List<Doctor> getAllDoctors(){
        return doctorRepository.findAll();
    }
}
