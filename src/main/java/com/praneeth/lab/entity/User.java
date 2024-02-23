package com.praneeth.lab.entity;


import com.praneeth.lab.enums.common.AccountVerifyStatus;
import com.praneeth.lab.enums.common.ActiveStatus;
import com.praneeth.lab.enums.common.Gender;
import com.praneeth.lab.enums.common.UserRole;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.util.Date;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String userUniqueId;

    @Column(nullable = false, unique = false)
    private String nic;

    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String userName;//email

    private String bloodGroup;

    private String address;

    @Lob
    private String current_verify_token;

    private int filedLoginAttemptCount;

    @Column(nullable = true)
    private String mobile;

    @JsonFormat(pattern = "dd-MM-yyyy HH:MM:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dob;

    @JsonFormat(pattern = "dd-MM-yyyy HH:MM:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date email_verify_link_timestamp;

    @JsonFormat(pattern = "dd-MM-yyyy HH:MM:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLogOutTimestamp;

    @JsonFormat(pattern = "dd-MM-yyyy HH:MM:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @JsonFormat(pattern = "dd-MM-yyyy HH:MM:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated;

    @Enumerated(value = EnumType.STRING)
    private ActiveStatus status;

    @Enumerated(value = EnumType.STRING)
    private UserRole userRole;

    @Enumerated(value = EnumType.STRING)
    private AccountVerifyStatus account_verify_status;

}
