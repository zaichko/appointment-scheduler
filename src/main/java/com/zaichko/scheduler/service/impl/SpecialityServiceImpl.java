package com.zaichko.scheduler.service.impl;

import com.zaichko.scheduler.dto.request.CreateSpecialityRequest;
import com.zaichko.scheduler.dto.request.UpdateSpecialityRequest;
import com.zaichko.scheduler.dto.response.SpecialityResponse;
import com.zaichko.scheduler.entity.Speciality;
import com.zaichko.scheduler.exception.NotFoundException;
import com.zaichko.scheduler.exception.SpecialityInUseException;
import com.zaichko.scheduler.mapper.SpecialityMapper;
import com.zaichko.scheduler.repository.DoctorRepository;
import com.zaichko.scheduler.repository.SpecialityRepository;
import com.zaichko.scheduler.service.SpecialityService;
import jakarta.validation.ValidationException;
import jakarta.validation.constraints.Positive;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SpecialityServiceImpl implements SpecialityService {
    private final SpecialityRepository specialityRepository;
    private final SpecialityMapper specialityMapper;
    private final DoctorRepository doctorRepository;

    private void validateNameHelper(String name) {
        if (name.isBlank()){
            throw new ValidationException("Speciality name must not be blank.");
        }
        if (specialityRepository.existsByName(name)) {
            throw new ValidationException("Speciality with this name already exists.");
        }
    }

    private void validateCodeHelper(String code){
        if (specialityRepository.existsByCode(code)){
            throw new ValidationException("Speciality with this code already exists.");
        }
    }

    @Override
    public List<SpecialityResponse> getAllSpecialities(){
        List<Speciality> specialities = specialityRepository.findAll();
        if (specialities.isEmpty()){
            return Collections.emptyList();
        }

        ArrayList<SpecialityResponse> specialityResponses = new ArrayList<>();

        for (Speciality speciality : specialities){
            specialityResponses.add(specialityMapper.toResponse(speciality));
        }

        return specialityResponses;
    }

    @Override
    public SpecialityResponse getSpecialityById(Long id){
        Speciality speciality = specialityRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Speciality not found."));

        return specialityMapper.toResponse(speciality);
    }

    @Override
    public SpecialityResponse createSpeciality(CreateSpecialityRequest request){
        validateNameHelper(request.getName());
        validateCodeHelper(request.getCode());

        Speciality speciality = new Speciality(request.getName(), request.getCode(), request.getDescription());

        Speciality savedSpeciality = specialityRepository.save(speciality);

        return specialityMapper.toResponse(savedSpeciality);
    }

    @Override
    public SpecialityResponse updateSpeciality(UpdateSpecialityRequest request){
        Speciality speciality = specialityRepository.findById(request.getId())
                .orElseThrow(() -> new NotFoundException("Speciality not found."));

        if (!speciality.getName().equals(request.getName())){
            validateNameHelper(request.getName());
        }
        if (!speciality.getCode().equals(request.getCode())){
            validateCodeHelper(request.getCode());
        }

        speciality.setName(request.getName());
        speciality.setCode(request.getCode());
        speciality.setDescription(request.getDescription());

        Speciality savedSpeciality = specialityRepository.save(speciality);

        return specialityMapper.toResponse(savedSpeciality);
    }

    @Override
    public SpecialityResponse changeSpecialityStatus(UpdateSpecialityRequest request){
        Speciality speciality = specialityRepository.findById(request.getId())
                .orElseThrow(() -> new NotFoundException("Speciality not found."));

        speciality.setActive(!speciality.isActive());
        Speciality savedSpeciality = specialityRepository.save(speciality);

        return specialityMapper.toResponse(savedSpeciality);
    }

    @Override
    public void deleteSpecialityById(Long id){
        if (!specialityRepository.existsById(id)){
            throw new NotFoundException("Speciality not found.");
        }

        if (doctorRepository.existsBySpecialitiesId(id)){
            throw new SpecialityInUseException("Speciality is in use and cannot be deleted.");
        }

        specialityRepository.deleteById(id);
    }
}
