package com.praneeth.lab.controller.UserController;

import com.praneeth.lab.config.security.custom.CustomUserAuthenticator;
import com.praneeth.lab.dto.appointment.AppointmentCreateDto;
import com.praneeth.lab.dto.appointment.AppointmentReportResDto;
import com.praneeth.lab.dto.appointment.AppointmentResDto;
import com.praneeth.lab.dto.common.CommonResponse;
import com.praneeth.lab.dto.medicaltest.MedicalTestResDto;
import com.praneeth.lab.enums.common.Status;
import com.praneeth.lab.service.AppointmentService;
import com.praneeth.lab.service.MedicalTestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

import static com.praneeth.lab.constants.AppConstants.DetailConstants.HEADER_AUTH;

@RestController
@RequestMapping(value = "/user")
@RequiredArgsConstructor
@Log4j2
public class UserController {
    private final MedicalTestService medicalTestService;
    private final AppointmentService appointmentService;
    @GetMapping(value = "/medical-test", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getMedicalTest() {
        List<MedicalTestResDto> res = medicalTestService.getAllMedicalTest();
        return ResponseEntity.ok(new CommonResponse<>(true,  res));
    }

    @PostMapping(value = "/appointment")
    public ResponseEntity<?> saveAppointment(@ModelAttribute AppointmentCreateDto dto, @RequestHeader(HEADER_AUTH) String token) {
        Long user_id = CustomUserAuthenticator.getUserIdFromToken(token);
        appointmentService.saveAppointment(dto, user_id);
        return ResponseEntity.ok(new CommonResponse<>(true,  "Appointment successfully created."));
    }


    @GetMapping(value = "/appointment")
    public ResponseEntity<?> saveAppointment(@RequestParam Status status,
                                             @RequestParam Date fromDate,
                                             @RequestParam Date toDate,
                                             @RequestHeader(HEADER_AUTH) String token) {
        Long user_id = CustomUserAuthenticator.getUserIdFromToken(token);
        List<AppointmentResDto> res = appointmentService.filterAppointmentForUser(user_id,fromDate, toDate , status);
        return ResponseEntity.ok(new CommonResponse<>(true, res));
    }

    @GetMapping(value = "/appointment/{appointmentId}/report")
    public ResponseEntity<?> getAppointmentReportForAdmin(@PathVariable("appointmentId") Long appointmentId, @RequestHeader(HEADER_AUTH) String token){
        Long user_id = CustomUserAuthenticator.getUserIdFromToken(token);
        List<AppointmentReportResDto> res = appointmentService.getAppointmentReportForUser(appointmentId, user_id);
        return ResponseEntity.ok(new CommonResponse<>(true,  res));


    }

}
