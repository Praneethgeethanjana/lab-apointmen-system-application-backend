package com.praneeth.lab.dto.appointment;

import com.praneeth.lab.enums.common.Status;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentReportResDto {
    private Long AppointmentId;
    private Status AppointmentStatus;
    private Date AppointmentDate;
    private Date AppointmentCretaedDate;
    private String paymentSlip;
    private BigDecimal total;
    private BigDecimal testFee;
    private String testName;
    private String reportUrl;
    private String note;
    private String userEmail;
    private String userFistName;
    private String userLastName;
    private String userCode;
}
