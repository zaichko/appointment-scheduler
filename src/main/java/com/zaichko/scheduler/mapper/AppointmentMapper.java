package com.zaichko.scheduler.mapper;

import com.zaichko.scheduler.dto.response.AppointmentResponse;
import com.zaichko.scheduler.entity.Appointment;
import org.springframework.stereotype.Component;

@Component
public class AppointmentMapper {
    public AppointmentResponse toResponse(Appointment appointment){
        String doctorFullName = String.format("%s %s", appointment.getTimeSlot().getDoctor().getUser().getFirstName(), appointment.getTimeSlot().getDoctor().getUser().getLastName()).trim();

        return new AppointmentResponse(
                appointment.getId(),
                appointment.getPatient().getFirstName(),
                doctorFullName,
                appointment.getTimeSlot().getStartTime(),
                appointment.getTimeSlot().getEndTime(),
                appointment.getStatus().name()
        );
    }
}
