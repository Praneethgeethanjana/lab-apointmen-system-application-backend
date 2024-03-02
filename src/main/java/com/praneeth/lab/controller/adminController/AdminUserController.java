package com.praneeth.lab.controller.adminController;


import com.praneeth.lab.dto.appointment.AppointmentReportResDto;
import com.praneeth.lab.dto.appointment.AppointmentResDto;
import com.praneeth.lab.dto.common.CommonResponse;
import com.praneeth.lab.dto.user.PublicUserResDto;
import com.praneeth.lab.enums.common.ActiveStatus;
import com.praneeth.lab.enums.common.Status;
import com.praneeth.lab.service.AppointmentService;
import com.praneeth.lab.service.PublicUserService;
import com.praneeth.lab.service.authService.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;


@RestController
@RequestMapping(value = "/admin")
@RequiredArgsConstructor
@Log4j2
public class AdminUserController {

    private final AppointmentService appointmentService;
    private final PublicUserService publicUserService;
    @GetMapping(value = "/appointment")
    public ResponseEntity<?> saveAppointment(@RequestParam String keyword,
                                             @RequestParam Status status,
                                             @RequestParam Date fromDate,
                                             @RequestParam Date toDate){
        List<AppointmentResDto> res = appointmentService.filterAppointmentForAdmin(fromDate, toDate, keyword, status);
        return ResponseEntity.ok(new CommonResponse<>(true,  res));


    }

    @GetMapping(value = "/user-list")
    public ResponseEntity<?> getUserList(@RequestParam String keyword,
                                         @RequestParam ActiveStatus status){
        List<PublicUserResDto> res = publicUserService.filterAllUserForAdmin(keyword, status);
        return ResponseEntity.ok(new CommonResponse<>(true,  res));


    }

    @PostMapping(value = "/appointment/status")
    public ResponseEntity<?> changeAppointmentStatus(@RequestParam Long appointmentId,
                                         @RequestParam Status status){
        appointmentService.changeAppointmentStatus(appointmentId, status);
        return ResponseEntity.ok(new CommonResponse<>(true,  "Appointment status successfully updated!"));


    }

    @PostMapping(value = "/appointment/{appointment_report_no}/report")
    public ResponseEntity<?> uploadReportToAppointment(@PathVariable("appointment_report_no") Long appointment_report_no,
                                                       @ModelAttribute String note,
                                                       @ModelAttribute MultipartFile report){
        appointmentService.uploadReportToAppointment(appointment_report_no, report, note);
        return ResponseEntity.ok(new CommonResponse<>(true,  "Report successfully uploaded!"));


    }

    @GetMapping(value = "/appointment/{appointmentId}/report")
    public ResponseEntity<?> getAppointmentReportForAdmin(@PathVariable("appointmentId") Long appointmentId){
        List<AppointmentReportResDto> res = appointmentService.getAppointmentReportForAdmin(appointmentId);
        return ResponseEntity.ok(new CommonResponse<>(true,  res));

    }

}
