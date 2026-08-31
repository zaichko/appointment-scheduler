package com.zaichko.scheduler.mapper;

import com.zaichko.scheduler.dto.response.SpecialityResponse;
import com.zaichko.scheduler.entity.Speciality;
import org.springframework.stereotype.Component;

@Component
public class SpecialityMapper {
    public SpecialityResponse toResponse(Speciality speciality){
        return new SpecialityResponse(
                speciality.getId(),
                speciality.getName(),
                speciality.getCode(),
                speciality.getDescription(),
                speciality.isActive()
        );
    }
}
