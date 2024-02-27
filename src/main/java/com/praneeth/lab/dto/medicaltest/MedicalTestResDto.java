package com.praneeth.lab.dto.medicaltest;

import lombok.*;
import java.math.BigDecimal;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MedicalTestResDto {

    private Long id;
    private String testName;
    private String description;
    private BigDecimal fee;

}
