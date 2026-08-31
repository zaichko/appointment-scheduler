package com.zaichko.scheduler.controller;

import com.zaichko.scheduler.dto.request.CreateDoctorRequest;
import com.zaichko.scheduler.dto.request.UpdateDoctorRequest;
import com.zaichko.scheduler.dto.request.UpdateDoctorSpecialitiesRequest;
import com.zaichko.scheduler.dto.response.DoctorResponse;
import com.zaichko.scheduler.service.impl.DoctorServiceImpl;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctors")
public class DoctorController {
    private final DoctorServiceImpl doctorService;

    public DoctorController(DoctorServiceImpl doctorService){
        this.doctorService = doctorService;
    }

    @GetMapping
    public List<DoctorResponse> getAllDoctors(){
        return doctorService.getAllDoctors();
    }

    @GetMapping("/{id}")
    public DoctorResponse getDoctorById(@PathVariable @Positive Long id){
        return doctorService.getDoctorById(id);
    }

    @PostMapping
    public DoctorResponse createDoctorProfile(@Valid @RequestBody CreateDoctorRequest request){
        return doctorService.createDoctor(request);
    }

    @PutMapping
    public DoctorResponse updateDoctor(@Valid @RequestBody UpdateDoctorRequest request){
        return doctorService.updateDoctor(request);
    }

    @PatchMapping
    public DoctorResponse updateDoctorSpecialities(@Valid @RequestBody UpdateDoctorSpecialitiesRequest request){
        return doctorService.updateDoctorSpecialities(request);
    }

    @DeleteMapping("/{id}")
    public void deleteDoctorById(@PathVariable @Positive Long id){
        doctorService.deleteDoctorById(id);
    }
}
