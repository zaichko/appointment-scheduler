package com.zaichko.scheduler.service;

import com.zaichko.scheduler.dto.request.CreateDoctorRequest;
import com.zaichko.scheduler.dto.request.UpdateDoctorRequest;
import com.zaichko.scheduler.dto.request.UpdateDoctorSpecialitiesRequest;
import com.zaichko.scheduler.dto.response.DoctorResponse;

import java.util.List;

public interface DoctorService {
    List<DoctorResponse> getAllDoctors();

    DoctorResponse getDoctorById(Long id);

    DoctorResponse createDoctor(CreateDoctorRequest request);

    DoctorResponse updateDoctor(UpdateDoctorRequest request);

    DoctorResponse updateDoctorSpecialities(UpdateDoctorSpecialitiesRequest request);

    void deleteDoctorById(Long id);
}
