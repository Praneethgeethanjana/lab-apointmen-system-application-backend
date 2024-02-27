package com.praneeth.lab.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.praneeth.lab.enums.common.Status;
import com.praneeth.lab.enums.common.TypeOfTest;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private TypeOfTest typeOfTest;

    @Lob
    private String remark;

    @Enumerated(value = EnumType.STRING)
    private Status status;

    @Lob
    private String doctorReceiptUrl;

    @Lob
    private String paymentSlipUrl;

    @JsonFormat(pattern = "dd-MM-yyyy HH:MM:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date appointmentDate;

    @JsonFormat(pattern = "dd-MM-yyyy HH:MM:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @JsonFormat(pattern = "dd-MM-yyyy HH:MM:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated;


}
