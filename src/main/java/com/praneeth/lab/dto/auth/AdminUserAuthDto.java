package com.praneeth.lab.dto.auth;

import com.praneeth.lab.dto.user.PublicUserResDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;



@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
@SuperBuilder
public class AdminUserAuthDto extends PublicUserResDto implements CommonUserAuth {
}
