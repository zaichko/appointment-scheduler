package com.zaichko.scheduler.service;

import com.zaichko.scheduler.dto.request.CreateSpecialityRequest;
import com.zaichko.scheduler.dto.request.UpdateSpecialityRequest;
import com.zaichko.scheduler.dto.response.SpecialityResponse;

import java.util.List;

public interface SpecialityService {
    List<SpecialityResponse> getAllSpecialities();

    SpecialityResponse getSpecialityById(Long id);

    SpecialityResponse createSpeciality(CreateSpecialityRequest request);

    SpecialityResponse updateSpeciality(Long id, UpdateSpecialityRequest request);

    SpecialityResponse changeSpecialityStatus(Long id);

    void deleteSpecialityById(Long id);
}
