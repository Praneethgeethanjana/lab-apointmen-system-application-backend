package com.praneeth.lab.dto.user;


import com.praneeth.lab.enums.common.Gender;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@SuperBuilder
public class PublicUserReqDto {

    private String nic;
    private Gender gender;
    private String userUniqueId;
    private String firstName;
    private String lastName;
    private String password;
    private String userName;
    private String bloodGroup;
    private String address;
    private String mobile;
    private Date dob;

}
