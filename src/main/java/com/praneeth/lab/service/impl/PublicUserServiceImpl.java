package com.praneeth.lab.service.impl;

import com.praneeth.lab.constants.EmailHtmlConstant;
import com.praneeth.lab.dto.user.*;

import com.praneeth.lab.entity.User;
import com.praneeth.lab.enums.common.AccountVerifyStatus;
import com.praneeth.lab.enums.common.ActiveStatus;
import com.praneeth.lab.enums.common.UserRole;
import com.praneeth.lab.exception.dto.CustomServiceException;
import com.praneeth.lab.repository.*;
import com.praneeth.lab.service.PublicUserService;
import com.praneeth.lab.utilities.EmailSender;
import com.praneeth.lab.utilities.EmailVerificationTokenGenerator;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.*;

import static com.praneeth.lab.constants.AppConstants.Email.*;
import static com.praneeth.lab.constants.AppConstants.ErrorConstants.EMAIL_RESEND_ERROR;
import static com.praneeth.lab.constants.AppConstants.ErrorConstants.TOKEN_HAS_EXPIRED;
import static com.praneeth.lab.constants.AppConstants.NotFoundConstants.*;
import static com.praneeth.lab.exception.constants.ErrorCodeConstants.*;


@Log4j2
@RequiredArgsConstructor
@Service
public class PublicUserServiceImpl implements PublicUserService {


    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final UserRepository  userRepository;
    private final EmailSender emailSender;
    private final EmailVerificationTokenGenerator emailVerificationTokenGenerator;

    @Value("${frontend.base.url}")
    private String frontendBaseUrl;




    @Override
    public void userSignUp(PublicUserReqDto dto) {
        log.info("Execute method userSignUp {}", dto.toString());

        if (userRepository.findFirstByUserName(dto.getUserName()).isPresent()){
            throw new CustomServiceException(DUPLICATE_ENTRY,"Email already exists!");
        }

        String latestUserUniqueId = userRepository.findLatestUserUniqueId();

        if (latestUserUniqueId==null){
            latestUserUniqueId = "ABC-LAB-1";
        }else{
            String[] split = latestUserUniqueId.split("-");
            long l = Long.parseLong(split[2]);
            l= l+1;
            latestUserUniqueId = "ABC-LAB-"+l;
        }

        try {
            User user = modelMapper.map(dto, User.class);
            user.setFiledLoginAttemptCount(0);
            user.setStatus(ActiveStatus.PENDING);
            user.setAccount_verify_status(AccountVerifyStatus.NOT_VERIFY);
            user.setUserRole(UserRole.USER);
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
            user.setCreated(new Date());
            user.setUpdated(new Date());
            user.setUserUniqueId(latestUserUniqueId);

            userRepository.save(user);

            this.sendVerificationEmail(user);
        }catch (Exception ex){
            log.error("Method userSignUp => {}", ex.getMessage());
            throw ex;
        }
    }



    @Override
    public PublicUserResDto updateUserProfile(PublicUserUpdateReqDto dto, Long userId) {
        log.info("Execute method userDetailUpdate {}", dto.toString());
        try{
        User user = userRepository.findById(userId).orElseThrow(() -> new CustomServiceException(RESOURCE_NOT_FOUND, NO_USER_FOUND));


            if (userRepository.getUserByUserNameNotInID(dto.getUserName(), user.getId()).isPresent()){
                throw new CustomServiceException(DUPLICATE_ENTRY,"Username already exists!");
            }

            /*if (userRepository.getUserByUserNameNotInWalletAddress(user.getWalletAddress(), userId).isPresent()){
                throw new CustomServiceException(DUPLICATE_ENTRY,"Wallet Address already exists!");
            }*/

            modelMapper.map(dto, user);
            User saveUser = userRepository.save(user);
            return modelMapper.map(saveUser, PublicUserResDto.class);


        }catch (Exception ex){
                log.error("Method userUpdate Details => {}", ex.getMessage());
                throw ex;
            }
    }

    @Override
    public Page<PublicUserResDto> getAllActiveUser(String keyword, ActiveStatus status, Pageable pageable) {
        log.info("Execute method getAllActiveUser");
        try {
            if (keyword==null || keyword.isEmpty()){
                return userRepository.getAllUserByStatus(status, pageable)
                        .map(user -> {
                            PublicUserResDto map = modelMapper.map(user, PublicUserResDto.class);
                            return map;
                        });
            }else {
                return userRepository.getAllUserByStatusAndEmail(status, keyword, pageable)
                        .map(user -> {
                            PublicUserResDto map = modelMapper.map(user, PublicUserResDto.class);
                            return map;
                        });
            }
        }catch (Exception ex){
            log.error(ex.getMessage());
            throw ex;
        }


    }

    @Override
    public void resendVerifyEmail(String email) {
        User user = userRepository.findFirstByUserName(email).orElseThrow(() -> new CustomServiceException(RESOURCE_NOT_FOUND,NO_USER_FOUND));
        if (user.getAccount_verify_status() == AccountVerifyStatus.VERIFY)
            throw new CustomServiceException("Account Already verified!");
        Date modifiedDate = user.getEmail_verify_link_timestamp();
        Date currentDate = new Date();
        double diff = currentDate.getTime() - modifiedDate.getTime();
        double diffMinutes = diff / (60 * 1000) % 60;

        if (diffMinutes < 1)
            throw new CustomServiceException(SYSTEM_ERROR, EMAIL_RESEND_ERROR);
        this.sendVerificationEmail(user);
    }


    @Override
    public void sendForgotPasswordVerifyLink(String email) {
        User user = userRepository.findFirstByUserName(email).orElseThrow(() -> new CustomServiceException(RESOURCE_NOT_FOUND,NO_USER_FOUND));
        if (user.getAccount_verify_status() == AccountVerifyStatus.NOT_VERIFY)
            throw new CustomServiceException("Your account is not yet verified. Please verify your account first!");
        if (user.getStatus() == ActiveStatus.BLOCK)
            throw new CustomServiceException("This account has been deactivated. please contact your organization admin");

        String token = emailVerificationTokenGenerator.generate(user.getId(), user.getUserName());
        try {
            emailSender.sendSimpleEmail(user.getUserName(), FORGOT_PASSWORD,
                    EmailHtmlConstant.sendForgotPasswordEmailBody(
                            FORGOT_PASSWORD_URL.replace("{frontend_base_url}", frontendBaseUrl).replace("{token}", token), (user)));

            user.setCurrent_verify_token(token);
            user.setEmail_verify_link_timestamp(new Date());
            userRepository.save(user);
        } catch (MessagingException e) {
            throw new CustomServiceException(e.getMessage());
        }
    }

    @Override
    public void updateForgetPassword(String token, String password) {
        log.info("Start function updateForgetPassword");
        try {
            Jws<Claims> claimsJws = emailVerificationTokenGenerator.verify(token);
            if (claimsJws == null) throw new CustomServiceException("Your verification link has expired");

            long userId = Long.parseLong(claimsJws.getBody().getSubject());
            String email = (String) claimsJws.getBody().get(EmailVerificationTokenGenerator.EMAIL_CLAIM);
            User user = userRepository.findUserByIdAndUserName(userId, email)
                    .orElseThrow(() -> new CustomServiceException(NO_USER_FOUND));

            if (!Objects.equals(user.getCurrent_verify_token(), token)) {
                throw new CustomServiceException(TOKEN_HAS_EXPIRED);
            }

            if (user.getAccount_verify_status() == AccountVerifyStatus.NOT_VERIFY)
                throw new CustomServiceException("Your account is not yet verified. Please verify your account first!");
            if (user.getStatus() == ActiveStatus.BLOCK)
                throw new CustomServiceException("This account has been deactivated. please contact your organization admin");

            user.setPassword(passwordEncoder.encode(password));
            user.setFiledLoginAttemptCount(0);
            user.setLastLogOutTimestamp(new Date());
            user.setCurrent_verify_token(null);
            user.setUpdated(new Date());
            userRepository.save(user);
        } catch (Exception ex) {
            log.error("updateForgetPassword : {}", ex.getMessage());
            throw ex;
        }
    }

    @Override
    public boolean checkPasswordResetToken(String token) {
        log.info("Start function updateForgetPassword");
        try {
            Jws<Claims> claimsJws = emailVerificationTokenGenerator.verify(token);
            if (claimsJws == null) throw new CustomServiceException(VERIFY_TOKEN_EXPIRED, TOKEN_HAS_EXPIRED);

            long userId = Long.parseLong(claimsJws.getBody().getSubject());
            String email = (String) claimsJws.getBody().get(EmailVerificationTokenGenerator.EMAIL_CLAIM);
            User user = userRepository.findUserByIdAndUserName(userId, email)
                    .orElseThrow(() -> new CustomServiceException(INVALID_VERIFY_TOKEN, "Your token appears to be invalid. Please try again later!"));

            if (!Objects.equals(user.getCurrent_verify_token(), token)){
                throw new CustomServiceException(VERIFY_TOKEN_EXPIRED,TOKEN_HAS_EXPIRED);
            }
            return true;
        } catch (Exception ex) {
            log.error("checkPasswordResetToken : {}", ex.getMessage());
            throw ex;
        }
    }

    @Override
    public void verifyAccount(String token) {
        log.info("Start function verifyAccount");
        try {
            Jws<Claims> claimsJws = emailVerificationTokenGenerator.verify(token);
            if (claimsJws == null) throw new CustomServiceException("Your verification link has expired");

            long userId = Long.parseLong(claimsJws.getBody().getSubject());
            String email = (String) claimsJws.getBody().get(EmailVerificationTokenGenerator.EMAIL_CLAIM);
            User user = userRepository.findUserByIdAndUserName(userId, email)
                    .orElseThrow(() -> new CustomServiceException(RESOURCE_NOT_FOUND,NO_USER_FOUND));

            if (!Objects.equals(user.getCurrent_verify_token(), token)) {
                throw new CustomServiceException(TOKEN_HAS_EXPIRED);
            }

            if (user.getAccount_verify_status() == AccountVerifyStatus.VERIFY)
                throw new CustomServiceException("Account Already verified!");

            user.setAccount_verify_status(AccountVerifyStatus.VERIFY);
            userRepository.save(user);

        } catch (Exception ex) {
            log.error("verifyAccount : {}", ex.getMessage());
            throw ex;
        }
    }



    public void sendVerificationEmail(User user) {
        try {
            String token = emailVerificationTokenGenerator.generate(user.getId(), user.getUserName());
            emailSender.sendSimpleEmail(user.getUserName(), VERIFY_EMAIL,
                    EmailHtmlConstant.sendUserToVerificationLink(
                            EMAIL_CONFORM_URL.replace("{frontend_base_url}", frontendBaseUrl).replace("{token}", token), user));
            user.setCurrent_verify_token(token);
            user.setEmail_verify_link_timestamp(new Date());
            userRepository.save(user);
        } catch (MessagingException e) {
            throw new CustomServiceException(e.getMessage());
        }
    }

}
