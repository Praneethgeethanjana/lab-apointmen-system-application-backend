package com.praneeth.lab.dto.user;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@SuperBuilder
public class PublicUserUpdateReqDto {
    private String userUniqueId;
    private String firstName;
    private String lastName;
    private String userName;
    private String address;
    private String zipCode;
    private String mobile;
    private String country;
    private String walletAddress;

}
