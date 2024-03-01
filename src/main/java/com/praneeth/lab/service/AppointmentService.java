package com.praneeth.lab.service;


import com.praneeth.lab.dto.appointment.AppointmentCreateDto;

public interface AppointmentService {

    void saveAppointment(AppointmentCreateDto dto, Long userId);
}
