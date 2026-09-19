package com.zaichko.scheduler.service.impl;

import com.zaichko.scheduler.dto.request.CreateDoctorRequest;
import com.zaichko.scheduler.dto.request.UpdateDoctorRequest;
import com.zaichko.scheduler.dto.request.UpdateDoctorSpecialitiesRequest;
import com.zaichko.scheduler.dto.response.DoctorResponse;
import com.zaichko.scheduler.entity.Doctor;
import com.zaichko.scheduler.entity.Speciality;
import com.zaichko.scheduler.entity.User;
import com.zaichko.scheduler.enums.Role;
import com.zaichko.scheduler.exception.*;
import com.zaichko.scheduler.mapper.DoctorMapper;
import com.zaichko.scheduler.repository.*;
import com.zaichko.scheduler.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class DoctorServiceImpl implements DoctorService {
    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;
    private final UserRepository userRepository;
    private final SpecialityRepository specialityRepository;
    private final AppointmentRepository appointmentRepository;
    private final TimeSlotRepository timeSlotRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public List<DoctorResponse> getAllDoctors(){
        List<Doctor> doctors = doctorRepository.findAll();

        return doctors.stream().map(doctorMapper :: toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public DoctorResponse getDoctorById(Long id){
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Doctor not found."));
        return doctorMapper.toResponse(doctor);
    }

    @Override
    public DoctorResponse createDoctor(CreateDoctorRequest request) {
        if (userRepository.existsByEmail(request.email())){
            throw new ConflictException("User with this email already exists.");
        }

        Set<Speciality> specialities = new HashSet<>(specialityRepository.findAllById(request.specialitiesId()));
        if (specialities.size() != request.specialitiesId().size()){
            throw new NotFoundException("One or more specialities not found.");
        }

        String encodedPassword = passwordEncoder.encode(request.password());

        User user = new User(request.email(), encodedPassword, request.firstName(), request.lastName(), Role.DOCTOR);
        Doctor doctor = new Doctor(request.experience(), request.bio(), specialities, user);
        userRepository.save(user);
        Doctor savedDoctor = doctorRepository.save(doctor);

        return doctorMapper.toResponse(savedDoctor);
    }

    @Override
    public DoctorResponse updateDoctor(Long id, UpdateDoctorRequest request){
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Doctor not found."));

        if (request.email() != null && !request.email().isBlank()
                && !Objects.equals(request.email(), doctor.getUser().getEmail())){
            if (userRepository.existsByEmail(request.email())){
                throw new ConflictException("User with this email already exists.");
            }
            doctor.getUser().setEmail(request.email());
        }
        if (request.password() != null && !request.password().isBlank()
                && !passwordEncoder.matches(request.password(), doctor.getUser().getPassword())){
            doctor.getUser().setPassword(passwordEncoder.encode(request.password()));
        }
        if (request.firstName() != null && !request.firstName().isBlank()
                && !Objects.equals(request.firstName(), doctor.getUser().getFirstName())){
            doctor.getUser().setFirstName(request.firstName());
        }
        if (request.lastName() != null && !request.lastName().isBlank()
                && !Objects.equals(request.lastName(), doctor.getUser().getLastName())){
            doctor.getUser().setLastName(request.lastName());
        }
        if (!Objects.equals(request.experience(), doctor.getExperience())) {
            doctor.setExperience(request.experience());
        }
        if (!Objects.equals(request.bio(), doctor.getBio())) {
            doctor.setBio(request.bio());
        }

        userRepository.save(doctor.getUser());
        Doctor savedDoctor = doctorRepository.save(doctor);

        return doctorMapper.toResponse(savedDoctor);
    }

    @Override
    public DoctorResponse updateDoctorSpecialities(Long id, UpdateDoctorSpecialitiesRequest request){
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Doctor not found."));

        Set<Speciality> specialities = new HashSet<>(specialityRepository.findAllById(request.specialitiesId()));
        if (specialities.size() != request.specialitiesId().size()){
            throw new NotFoundException("One or more specialities not found.");
        }

        doctor.setSpecialities(specialities);
        Doctor savedDoctor = doctorRepository.save(doctor);

        return doctorMapper.toResponse(savedDoctor);
    }

    @Override
    public void deleteDoctorById(Long id){
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Doctor not found."));
        Long userId = doctor.getUser().getId();

        if (appointmentRepository.existsByTimeSlotDoctorId(id)){
            throw new ConflictException("Doctor has appointments and cannot be deleted.");
        }
        if (timeSlotRepository.existsByDoctorId(id)){
            throw new ConflictException("Doctor has time slots and cannot be deleted.");
        }

        doctorRepository.deleteById(id);
        userRepository.deleteById(userId);
    }
}
