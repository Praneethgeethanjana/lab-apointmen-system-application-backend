package com.praneeth.lab.service;


import com.praneeth.lab.dto.appointment.AppointmentCreateDto;
import com.praneeth.lab.dto.appointment.AppointmentReportResDto;
import com.praneeth.lab.dto.appointment.AppointmentResDto;
import com.praneeth.lab.enums.common.Status;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

public interface AppointmentService {

    void saveAppointment(AppointmentCreateDto dto, Long userId);

    List<AppointmentResDto> filterAppointmentForAdmin(Date fromDate, Date toDate, String keyword, Status status);

    List<AppointmentResDto> filterAppointmentForUser(Long userId,Date fromDate, Date toDate, Status status);

    void changeAppointmentStatus(Long appointmentId, Status status);

    void uploadReportToAppointment(Long appointmentDetailsId,MultipartFile report, String note);

    List<AppointmentReportResDto> getAppointmentReportForAdmin(Long appointmentId);

    List<AppointmentReportResDto> getAppointmentReportForUser(Long appointmentId, Long userId);
}
