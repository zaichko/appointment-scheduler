package com.zaichko.scheduler.service;

import com.zaichko.scheduler.entity.Speciality;
import com.zaichko.scheduler.repository.SpecialityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecialityService {
    private final SpecialityRepository specialityRepository;

    public SpecialityService(SpecialityRepository specialityRepository){
        this.specialityRepository = specialityRepository;
    }

    public List<Speciality> getAllSpecialities(){
        return specialityRepository.findAll();
    }
}
