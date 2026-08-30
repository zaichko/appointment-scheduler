package com.zaichko.scheduler.mapper;

import com.zaichko.scheduler.dto.response.DoctorResponse;
import com.zaichko.scheduler.entity.Doctor;
import com.zaichko.scheduler.entity.Speciality;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DoctorMapper {
    public DoctorResponse toResponse(@NonNull Doctor doctor){
        String doctorFullName = String.format("%s %s", doctor.getUser().getFirstName(), doctor.getUser().getLastName());
        Set<Speciality> specialities = doctor.getSpecialities();
        ArrayList<String> specialitiesNames = new ArrayList<>();

        for (Speciality speciality : specialities){
            specialitiesNames.add(speciality.getName());
        }

        return new DoctorResponse(
                doctor.getId(),
                doctor.getUser().getId(),
                doctorFullName,
                doctor.getUser().getEmail(),
                doctor.getExperience(),
                doctor.getBio(),
                specialitiesNames
        );
    }
}
