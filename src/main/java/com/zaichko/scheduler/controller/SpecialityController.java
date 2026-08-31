package com.zaichko.scheduler.controller;

import com.zaichko.scheduler.dto.request.CreateSpecialityRequest;
import com.zaichko.scheduler.dto.request.UpdateSpecialityRequest;
import com.zaichko.scheduler.dto.response.SpecialityResponse;
import com.zaichko.scheduler.service.impl.SpecialityServiceImpl;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/specialities")
public class SpecialityController {
    private final SpecialityServiceImpl specialityService;

    @GetMapping
    public List<SpecialityResponse> getAllSpecialities(){
        return specialityService.getAllSpecialities();
    }

    @GetMapping("/{id}")
    public SpecialityResponse getSpecialityById(@PathVariable @Positive Long id){
        return specialityService.getSpecialityById(id);
    }

    @PostMapping
    public SpecialityResponse createSpeciality(@Valid @RequestBody CreateSpecialityRequest request){
        return specialityService.createSpeciality(request);
    }

    @PutMapping
    public SpecialityResponse updateSpeciality(@Valid @RequestBody UpdateSpecialityRequest request){
        return specialityService.updateSpeciality(request);
    }

    @PatchMapping("/status")
    public SpecialityResponse changeStatus(@Valid @RequestBody UpdateSpecialityRequest request){
        return specialityService.changeSpecialityStatus(request);
    }

    @DeleteMapping("/{id}")
    public void deleteSpeciality(@PathVariable @Positive Long id){
        specialityService.deleteSpecialityById(id);
    }
}
