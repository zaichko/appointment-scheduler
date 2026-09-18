package com.zaichko.scheduler.service.impl;

import com.zaichko.scheduler.dto.request.CreateSpecialityRequest;
import com.zaichko.scheduler.dto.request.UpdateSpecialityRequest;
import com.zaichko.scheduler.dto.response.SpecialityResponse;
import com.zaichko.scheduler.entity.Speciality;
import com.zaichko.scheduler.exception.ConflictException;
import com.zaichko.scheduler.exception.NotFoundException;
import com.zaichko.scheduler.mapper.SpecialityMapper;
import com.zaichko.scheduler.repository.DoctorRepository;
import com.zaichko.scheduler.repository.SpecialityRepository;
import com.zaichko.scheduler.service.SpecialityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
@Transactional
public class SpecialityServiceImpl implements SpecialityService {
    private final SpecialityRepository specialityRepository;
    private final SpecialityMapper specialityMapper;
    private final DoctorRepository doctorRepository;

    private void validateNameHelper(String name) {
        if (name == null || name.isBlank()){
            throw new IllegalArgumentException("Speciality name must not be blank.");
        }
        if (specialityRepository.existsByName(name)) {
            throw new ConflictException("Speciality with this name already exists.");
        }
    }

    private void validateCodeHelper(String code){
        if (specialityRepository.existsByCode(code)){
            throw new ConflictException("Speciality with this code already exists.");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<SpecialityResponse> getAllSpecialities(){
        List<Speciality> specialities = specialityRepository.findAll();

        return specialities.stream().map(specialityMapper :: toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public SpecialityResponse getSpecialityById(Long id){
        Speciality speciality = specialityRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Speciality not found."));
        return specialityMapper.toResponse(speciality);
    }

    @Override
    public SpecialityResponse createSpeciality(CreateSpecialityRequest request){
        validateNameHelper(request.name());
        validateCodeHelper(request.code());

        Speciality speciality = new Speciality(request.name(), request.code(), request.description());
        Speciality savedSpeciality = specialityRepository.save(speciality);

        return specialityMapper.toResponse(savedSpeciality);
    }

    @Override
    public SpecialityResponse updateSpeciality(Long id, UpdateSpecialityRequest request){
        Speciality speciality = specialityRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Speciality not found."));

        if (!Objects.equals(request.name(), speciality.getName()) && request.name() != null){
            validateNameHelper(request.name());
            speciality.setName(request.name());
        }
        if (!Objects.equals(request.code(), speciality.getCode())){
            validateCodeHelper(request.code());
            speciality.setCode(request.code());
        }
        if (!Objects.equals(request.description(), speciality.getDescription())) {
            speciality.setDescription(request.description());
        }

        Speciality savedSpeciality = specialityRepository.save(speciality);

        return specialityMapper.toResponse(savedSpeciality);
    }

    @Override
    public SpecialityResponse changeSpecialityStatus(Long id){
        Speciality speciality = specialityRepository.findById(id)
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
            throw new ConflictException("Speciality is in use and cannot be deleted.");
        }

        specialityRepository.deleteById(id);
    }
}
