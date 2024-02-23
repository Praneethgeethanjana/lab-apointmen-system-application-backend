package com.praneeth.lab.service;

import com.praneeth.lab.dto.user.*;
import com.praneeth.lab.entity.User;
import com.praneeth.lab.enums.common.ActiveStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;


public interface PublicUserService {
    void userSignUp(PublicUserReqDto dto);

    PublicUserResDto updateUserProfile(PublicUserUpdateReqDto dto, Long userId);

    Page<PublicUserResDto> getAllActiveUser(String keyword, ActiveStatus status, Pageable pageable);

    void resendVerifyEmail(String email);

    void sendForgotPasswordVerifyLink(String email);

    void updateForgetPassword(String token, String password);

    boolean checkPasswordResetToken(String token);

    void verifyAccount(String token);

}
