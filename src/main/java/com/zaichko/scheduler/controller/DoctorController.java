package com.zaichko.scheduler.controller;

import com.zaichko.scheduler.dto.request.CreateDoctorRequest;
import com.zaichko.scheduler.dto.request.UpdateDoctorRequest;
import com.zaichko.scheduler.dto.request.UpdateDoctorSpecialitiesRequest;
import com.zaichko.scheduler.dto.response.DoctorResponse;
import com.zaichko.scheduler.service.DoctorService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctors")
@RequiredArgsConstructor
public class DoctorController {
    private final DoctorService doctorService;

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

    @PutMapping("/{id}")
    public DoctorResponse updateDoctor(@PathVariable @Positive Long id, @Valid @RequestBody UpdateDoctorRequest request){
        return doctorService.updateDoctor(id, request);
    }

    @PatchMapping("/{id}/update_specialities")
    public DoctorResponse updateDoctorSpecialities(@PathVariable @Positive Long id, @Valid @RequestBody UpdateDoctorSpecialitiesRequest request){
        return doctorService.updateDoctorSpecialities(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteDoctor(@PathVariable @Positive Long id){
        doctorService.deleteDoctorById(id);
    }
}
