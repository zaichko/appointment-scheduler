package com.zaichko.scheduler.service;

import com.zaichko.scheduler.dto.request.CreateSpecialityRequest;
import com.zaichko.scheduler.dto.request.UpdateSpecialityRequest;
import com.zaichko.scheduler.dto.response.SpecialityResponse;

import java.util.List;

public interface SpecialityService {
    List<SpecialityResponse> getAllSpecialities();

    SpecialityResponse getSpecialityById(Long id);

    SpecialityResponse createSpeciality(CreateSpecialityRequest request);

    SpecialityResponse updateSpeciality(UpdateSpecialityRequest request);

    SpecialityResponse changeSpecialityStatus(UpdateSpecialityRequest request);

    void deleteSpecialityById(Long id);
}
