package com.praneeth.lab.service;


import com.praneeth.lab.dto.appointment.AppointmentCreateDto;
import com.praneeth.lab.dto.appointment.AppointmentResDto;
import com.praneeth.lab.enums.common.Status;

import java.util.Date;
import java.util.List;

public interface AppointmentService {

    void saveAppointment(AppointmentCreateDto dto, Long userId);

    List<AppointmentResDto> filterAppointmentForAdmin(Date fromDate, Date toDate, String keyword, Status status);

    List<AppointmentResDto> filterAppointmentForUser(Long userId,Date fromDate, Date toDate, Status status);
}
