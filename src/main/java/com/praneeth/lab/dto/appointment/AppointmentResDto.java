package com.praneeth.lab.dto.appointment;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.praneeth.lab.dto.user.PublicUserResDto;
import com.praneeth.lab.entity.AppointmentDetails;
import com.praneeth.lab.entity.User;
import com.praneeth.lab.enums.common.Status;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentResDto {

    private Long id;
    private String remark;
    private Status status;
    private String doctorReceiptUrl;
    private String paymentSlipUrl;
    private BigDecimal total;
    private Date appointmentDate;
    private Date created;
    private Date updated;
    private PublicUserResDto user;
    private List<AppointmentDetailsResDto> appointmentDetails = new ArrayList<>();


}
