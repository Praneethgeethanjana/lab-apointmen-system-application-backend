package com.praneeth.lab.service.authService;


import com.praneeth.lab.config.security.custom.CustomOauthException;
import com.praneeth.lab.config.security.custom.CustomUserAuthenticator;
import com.praneeth.lab.dto.auth.AdminUserAuthDto;
import com.praneeth.lab.dto.auth.UserAuthDto;
import com.praneeth.lab.entity.User;
import com.praneeth.lab.enums.common.AccountVerifyStatus;
import com.praneeth.lab.enums.common.ActiveStatus;
import com.praneeth.lab.enums.common.UserRole;
import com.praneeth.lab.exception.dto.CustomAuthenticationException;
import com.praneeth.lab.exception.dto.CustomServiceException;
import com.praneeth.lab.repository.UserRepository;
import com.praneeth.lab.config.security.SecurityConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static com.praneeth.lab.constants.AppConstants.ErrorConstants.*;
import static com.praneeth.lab.constants.AppConstants.NotFoundConstants.NO_USER_FOUND;


@Service
@RequiredArgsConstructor
@Log4j2
public class UserServiceImpl implements UserService {

    private final ModelMapper modelMapper;

    private final UserRepository userRepository;

    private final HttpServletRequest request;
    private final int MAX_ATTEMPT = 7;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("User login: " + username);
        /* gets current authentication principal */
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        String clientId = user.getUsername();

        /*check refresh token validity*/
        if (request.getParameter("grant_type").equalsIgnoreCase("refresh_token")) {
            log.info("start check refresh token validity");
            String refresh_token = request.getParameter("refresh_token");
            String role = CustomUserAuthenticator.getUserRoleFromToken(refresh_token);
            Long user_id = CustomUserAuthenticator.getUserIdFromToken(refresh_token);
            Date lastLogOutTime = CustomUserAuthenticator.getLastLogOutTimeFromToken(refresh_token);
            log.info("start Interceptor => preHandle  user_id : {}, role : {}, last logout time{}", user_id, role, lastLogOutTime);

            Date lastLogOutDateTime = this.getLastLogOutTime(user_id, role);
            if (lastLogOutDateTime.compareTo(lastLogOutTime) != 0)
                throw new CustomAuthenticationException(401, "Invalid token");
        }

        switch (clientId) {

            case SecurityConstants.USER_ID:
                User exiUser = userRepository.findActiveUserByUserNameOrEmail(username).orElseThrow(() -> new CustomServiceException("User Not Found!"));
                if (exiUser.getAccount_verify_status()== AccountVerifyStatus.NOT_VERIFY)
                    throw new CustomServiceException("Your account is not yet verified. Please verify your account first!");

                if (exiUser.getStatus() == ActiveStatus.BLOCK) throw new CustomOauthException(ACCOUNT_BLOCKED);
                    return new UserAuthDto(exiUser.getId(), username, exiUser.getPassword(), getRole(exiUser.getUserRole()),
                            modelMapper.map(exiUser, AdminUserAuthDto.class));

            case SecurityConstants.ADMIN_ID:

                User exiAdminUser = userRepository.findActiveAdminUserByUserNameOrEmail(username, UserRole.ADMIN).orElseThrow(() -> new CustomServiceException("Admin User Not Found!"));
                if (exiAdminUser.getAccount_verify_status()== AccountVerifyStatus.NOT_VERIFY)
                    throw new CustomServiceException("Your account is not yet verified. Please verify your account first!");

                if (exiAdminUser.getStatus() == ActiveStatus.BLOCK) throw new CustomOauthException(ACCOUNT_BLOCKED);

                if (MAX_ATTEMPT <= exiAdminUser.getFiledLoginAttemptCount()) {
                    exiAdminUser.setLastLogOutTimestamp(new Date());
                    userRepository.save(exiAdminUser);
                    throw new CustomOauthException(ACCOUNT_LOCKED);
                }
                return new UserAuthDto(exiAdminUser.getId(), username, exiAdminUser.getPassword(), getRole(exiAdminUser.getUserRole()),
                        modelMapper.map(exiAdminUser, AdminUserAuthDto.class));

            default:
                throw new CustomOauthException(INVALID_CLIENT_ID);

        }
    }


    private User getAdminUser(String username) {
        User adminUser = userRepository.findActiveAdminUserByUserNameOrEmail(username, UserRole.ADMIN).orElseThrow(() -> new CustomServiceException("Admin User Not Found!"));
        if (adminUser.getStatus() != ActiveStatus.ACTIVE)
            throw new CustomOauthException("this admin user is not active");
        return adminUser;
    }

    private List<SimpleGrantedAuthority> getRole(UserRole userRole) {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + userRole));
    }

    @Override
    public CustomOauthException loginFailed(String username) {
        log.info("start loginFailed");
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        String clientId = user.getUsername();

        if (Objects.equals(clientId, SecurityConstants.USER_ID)) {
            /*User adminUser = userRepository.findActiveUserByUserNameOrEmail(username).orElseThrow(() -> new CustomServiceException("User Not Found!"));
            adminUser.setFiledLoginAttemptCount(adminUser.getFiledLoginAttemptCount() + 1);
            log.info(adminUser.getFiledLoginAttemptCount());
            userRepository.save(adminUser);*/
            return new CustomOauthException(INVALID_LOGIN);
        }else if(Objects.equals(clientId, SecurityConstants.ADMIN_ID)){
            User adminUser = userRepository.findActiveAdminUserByUserNameOrEmail(username, UserRole.ADMIN).orElseThrow(() -> new CustomServiceException("Admin User Not Found!"));
            adminUser.setFiledLoginAttemptCount(adminUser.getFiledLoginAttemptCount() + 1);
            log.info(adminUser.getFiledLoginAttemptCount());
            userRepository.save(adminUser);
            return new CustomOauthException(INVALID_LOGIN_ATTEMPTS.replace("<count>", String.valueOf(MAX_ATTEMPT - adminUser.getFiledLoginAttemptCount())));
        }
        throw new CustomOauthException(INVALID_CLIENT_ID);

    }

    @Override
    public void loginSuccess(String username, String clientId) {
        try {
            log.info("start loginSuccess");
            if (Objects.equals(clientId, SecurityConstants.USER_ID)) {
                log.info("reset login attempt :: client_id{}, username, {}", clientId, username);
                User adminUser = userRepository.findActiveUserByUserNameOrEmail(username).orElseThrow(() -> new CustomServiceException("User Not Found!"));
                adminUser.setFiledLoginAttemptCount(0);
                userRepository.save(adminUser);
            }else if (Objects.equals(clientId, SecurityConstants.ADMIN_ID)){
                log.info("reset login attempt :: client_id{}, username, {}", clientId, username);
                User adminUser = userRepository.findActiveAdminUserByUserNameOrEmail(username, UserRole.ADMIN).orElseThrow(() -> new CustomServiceException("Admin User Not Found!"));
                adminUser.setFiledLoginAttemptCount(0);
                userRepository.save(adminUser);
            }
            else {
                throw new CustomOauthException(INVALID_CLIENT_ID);
            }
        } catch (Exception ex) {
            log.error("loginSuccess : {}", ex.getMessage());
            throw ex;
        }
    }

    @Override
    public void logout(long user_id, String role) {
        log.info("start function logout user_id : {}, role: {}", user_id, role);
        switch (role) {
            case "ROLE_ADMIN":
            case "ROLE_USER":
                User adminUser = userRepository.findById(user_id).orElseThrow(() -> new CustomOauthException(NO_USER_FOUND));
                adminUser.setLastLogOutTimestamp(new Date());
                userRepository.save(adminUser);
                break;
            default:
                throw new CustomOauthException("Invalid user role");
        }
    }


    @Override
    public Date getLastLogOutTimeForTokenGenerator(String username, String clientId) {

        switch (clientId) {
            case SecurityConstants.USER_ID:
                User user = userRepository.findActiveUserByUserNameOrEmail(username).orElseThrow(() -> new CustomOauthException(NO_USER_FOUND));
                return user.getLastLogOutTimestamp();
            case SecurityConstants.ADMIN_ID:
                User adminUser = userRepository.findActiveAdminUserByUserNameOrEmail(username, UserRole.ADMIN).orElseThrow(() -> new CustomServiceException("Admin User Not Found!"));
                return adminUser.getLastLogOutTimestamp();
            default:
                throw new CustomOauthException(INVALID_CLIENT_ID);
        }
    }


    @Override
    public Date getLastLogOutTime(Long user_id, String role) {
        User user = userRepository.findById(user_id).orElseThrow(() -> new CustomServiceException(NO_USER_FOUND));
        return user.getLastLogOutTimestamp();
    }



}

