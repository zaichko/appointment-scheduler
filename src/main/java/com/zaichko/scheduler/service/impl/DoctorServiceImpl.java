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
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {
    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;
    private final UserRepository userRepository;
    private final SpecialityRepository specialityRepository;
    private final AppointmentRepository appointmentRepository;
    private final TimeSlotRepository timeSlotRepository;
    private final UserServiceImpl userService;

    @Override
    public List<DoctorResponse> getAllDoctors(){
        List<Doctor> doctors = doctorRepository.findAll();
        if (doctors.isEmpty()){
            return Collections.emptyList();
        }

        ArrayList<DoctorResponse> doctorResponses = new ArrayList<>();

        for (Doctor doctor : doctors){
            doctorResponses.add(doctorMapper.toResponse(doctor));
        }

        return doctorResponses;
    }

    @Override
    public DoctorResponse getDoctorById(Long id){
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Doctor not found."));
        return doctorMapper.toResponse(doctor);
    }

    @Override
    @Transactional
    public DoctorResponse createDoctor(CreateDoctorRequest request) {
        if (userRepository.existsByEmail(request.getEmail())){
            throw new EmailAlreadyExistsException("User with this email already exists.");
        }

        User user = new User(request.getEmail(), request.getPassword(), request.getFirstName(), request.getLastName(), Role.DOCTOR);

        userRepository.save(user);

        Set<Speciality> specialities = new HashSet<>(specialityRepository.findAllById(request.getSpecialitiesId()));
        if (specialities.size() != request.getSpecialitiesId().size()){
            throw new NotFoundException("One or more specialities not found.");
        }

        Doctor doctor = new Doctor(request.getExperience(), request.getBio(), specialities, user);
        Doctor savedDoctor = doctorRepository.save(doctor);

        return doctorMapper.toResponse(savedDoctor);
    }

    @Override
    @Transactional
    public DoctorResponse updateDoctor(UpdateDoctorRequest request){
        Doctor doctor = doctorRepository.findById(request.getId())
                .orElseThrow(() -> new NotFoundException("Doctor not found."));

        if (!request.getEmail().isBlank()){
            doctor.getUser().setEmail(request.getEmail());
        }

        if (!request.getPassword().isBlank()){
            doctor.getUser().setPassword(request.getPassword());
        }

        if (!request.getFirstName().isBlank()){
            doctor.getUser().setFirstName(request.getFirstName());
        }

        if (!request.getLastName().isBlank()){
            doctor.getUser().setLastName(request.getLastName());
        }

        doctor.setExperience(request.getExperience());
        doctor.setBio(request.getBio());

        userRepository.save(doctor.getUser());
        Doctor savedDoctor = doctorRepository.save(doctor);

        return doctorMapper.toResponse(savedDoctor);
    }

    @Override
    public DoctorResponse updateDoctorSpecialities(UpdateDoctorSpecialitiesRequest request){
        Doctor doctor = doctorRepository.findByUserId(request.getId())
                .orElseThrow(() -> new NotFoundException("Doctor not found."));

        Set<Speciality> specialities = new HashSet<>(specialityRepository.findAllById(request.getSpecialitiesId()));
        if (specialities.size() != request.getSpecialitiesId().size()){
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

        if (appointmentRepository.existsByDoctorId(doctor.getId())){
            throw new UserHasAppointmentsException("Doctor has appointments.");
        }

        if (timeSlotRepository.existsByDoctorId(doctor.getId())){
            throw new DoctorHasTimeSlotsException("Doctor has time slots.");
        }

        userService.deleteUserById(doctor.getUser().getId());
    }
}
