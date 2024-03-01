package com.praneeth.lab.dto.user;


import com.praneeth.lab.enums.common.AccountVerifyStatus;
import com.praneeth.lab.enums.common.ActiveStatus;
import com.praneeth.lab.enums.common.UserRole;
import lombok.*;
import lombok.experimental.SuperBuilder;


import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@SuperBuilder
public class PublicUserResDto {
    private Long id;
    private String userUniqueId;
    private String firstName;
    private String lastName;
    private String userName;
    private String address;
    private String zipCode;
    private String walletAddress;
    private String mobile;
    private String country;
    private Long referralUserId;
    private Date created;
    private Date updated;
    private ActiveStatus status;
    private UserRole userRole;
    private AccountVerifyStatus account_verify_status;


}
