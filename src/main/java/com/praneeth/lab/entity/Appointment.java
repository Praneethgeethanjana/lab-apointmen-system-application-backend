package com.praneeth.lab.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.praneeth.lab.enums.common.Status;
import com.praneeth.lab.enums.common.TypeOfTest;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @Lob
    private String remark;

    @Enumerated(value = EnumType.STRING)
    private Status status;

    @Lob
    private String doctorReceiptUrl;

    @Lob
    private String paymentSlipUrl;

    @Digits(integer = 9, fraction = 2)
    private BigDecimal total;

    @JsonFormat(pattern = "dd-MM-yyyy HH:MM:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date appointmentDate;

    @JsonFormat(pattern = "dd-MM-yyyy HH:MM:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @JsonFormat(pattern = "dd-MM-yyyy HH:MM:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private User user;

    @OneToMany(mappedBy = "appointment")
    @ToString.Exclude
    private List<AppointmentDetails> appointmentDetails = new ArrayList<>();


}
