package com.zaichko.scheduler.controller;

import com.zaichko.scheduler.entity.Speciality;
import com.zaichko.scheduler.service.SpecialityService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/specialities")
public class SpecialityController {
    private final SpecialityService specialityService;

    public SpecialityController(SpecialityService specialityService){
        this.specialityService = specialityService;
    }

    @GetMapping
    public List<Speciality> getAllSpecialities(){
        return specialityService.getAllSpecialities();
    }
}
