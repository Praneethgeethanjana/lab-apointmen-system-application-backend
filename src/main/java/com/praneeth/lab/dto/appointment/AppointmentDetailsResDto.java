package com.praneeth.lab.dto.appointment;


import com.praneeth.lab.dto.medicaltest.MedicalTestResDto;
import com.praneeth.lab.entity.MedicalTest;
import lombok.*;


import java.math.BigDecimal;
import java.util.Date;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentDetailsResDto {

    private Long id;
    private MedicalTestResDto medicalTest;
    private BigDecimal fee;
    private Date created;
}
