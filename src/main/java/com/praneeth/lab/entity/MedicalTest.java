package com.praneeth.lab.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MedicalTest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String testName;

    private String description;

    @Digits(integer = 9, fraction = 2)
    private BigDecimal fee;

    @OneToMany(mappedBy = "medicalTest")
    @ToString.Exclude
    private List<AppointmentDetails> appointmentDetails = new ArrayList<>();

}
