package com.praneeth.lab.dto.appointment;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalTime;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentCreateDto {
    private String remark;
    private MultipartFile doctorReceiptUrl;
    private MultipartFile paymentSlipUrl;
    private Date appointmentDate;
    private String testIdList;
}
