package com.praneeth.lab.service.authService;

import com.praneeth.lab.config.security.custom.CustomOauthException;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Date;


public interface UserService extends UserDetailsService {

    CustomOauthException loginFailed(String username);

    void loginSuccess(String username, String clientId);

    void logout(long user_id, String role);

    Date getLastLogOutTime(Long user_id, String role);

    Date getLastLogOutTimeForTokenGenerator(String username, String clientId);


}
