package com.zaichko.scheduler.controller;

import com.zaichko.scheduler.dto.request.CreateSpecialityRequest;
import com.zaichko.scheduler.dto.request.UpdateSpecialityRequest;
import com.zaichko.scheduler.dto.response.SpecialityResponse;
import com.zaichko.scheduler.service.SpecialityService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/specialities")
public class SpecialityController {
    private final SpecialityService specialityService;

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

    @PutMapping("/{id}")
    public SpecialityResponse updateSpeciality(@PathVariable @Positive Long id, @Valid @RequestBody UpdateSpecialityRequest request){
        return specialityService.updateSpeciality(id, request);
    }

    @PatchMapping("/{id}/status")
    public SpecialityResponse changeStatus(@PathVariable @Positive Long id){
        return specialityService.changeSpecialityStatus(id);
    }

    @DeleteMapping("/{id}")
    public void deleteSpeciality(@PathVariable @Positive Long id){
        specialityService.deleteSpecialityById(id);
    }
}
