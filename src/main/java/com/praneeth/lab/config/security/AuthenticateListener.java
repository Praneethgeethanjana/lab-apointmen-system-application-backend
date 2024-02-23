package com.praneeth.lab.config.security;


import com.praneeth.lab.dto.auth.UserAuthDto;
import com.praneeth.lab.service.authService.UserService;
import com.praneeth.lab.utilities.IPAddressHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;



@Component
@Log4j2
@RequiredArgsConstructor
public class AuthenticateListener implements ApplicationListener<ApplicationEvent> {

    private final UserService userService;
    private final HttpServletRequest request;
    private final IPAddressHandler ipAddressHandler;


    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        try {
            if (event instanceof AuthenticationFailureBadCredentialsEvent) {
                /*onAuthenticationFailureEvent((AbstractAuthenticationFailureEvent) event);*/
                log.info("FailureEvent {}", (AbstractAuthenticationFailureEvent) event);
            } else if (event instanceof AuthenticationSuccessEvent) {
                onAuthenticationSuccessEvent((AuthenticationSuccessEvent) event);
            }
        } catch (Exception ex) {
            log.error("Authenticate Listener : {}", ex.getMessage());
        }
    }

    private void onAuthenticationFailureEvent(AbstractAuthenticationFailureEvent event) {
        try {
            log.info("get username in authenticate listener : {} " , request.getParameter("username"));
            if (event instanceof AuthenticationFailureBadCredentialsEvent) {
                Object details = SecurityContextHolder.getContext().getAuthentication().getDetails();
                if (details instanceof WebAuthenticationDetails) {
                    userService.loginFailed(request.getParameter("username"));
                    log.info("Authentication Failure IP Address : {}" , ((WebAuthenticationDetails) details).getRemoteAddress());
                }

                log.info("AuthenticationFailureBadCredentialsEvent : {} "  , request.getParameter("username"));
            } else if (event instanceof AuthenticationFailureCredentialsExpiredEvent) {
                // validated, but password expiration
                log.info("AuthenticationFailureCredentialsExpiredEvent : {} "  , request.getParameter("username"));
            } else if (event instanceof AuthenticationFailureDisabledEvent) {
                // verified but the account is disabled
                log.info("AuthenticationFailureDisabledEvent : {} "  , request.getParameter("username"));
            } else if (event instanceof AuthenticationFailureExpiredEvent) {
                // verified, but the account has expired
                log.info("AuthenticationFailureExpiredEvent: {} " , request.getParameter("username"));
            } else if (event instanceof AuthenticationFailureLockedEvent) {
                // the account is locked
                log.info("AuthenticationFailureLockedEvent: {}" ,request.getParameter("username"));
            } else if (event instanceof AuthenticationFailureProviderNotFoundEvent) {
                // configuration error, no suitable AuthenticationProvider to handle login validation
                log.info("AuthenticationFailureProviderNotFoundEvent: {} "  , request.getParameter("username"));
            } else if (event instanceof AuthenticationFailureProxyUntrustedEvent) {
                // untrusted proxy for Oauth, CAS-party verification of such a situation, mostly configuration error
                log.info("AuthenticationFailureProxyUntrustedEvent: {} "  , request.getParameter("username"));
            } else if (event instanceof AuthenticationFailureServiceExceptionEvent) {
                // any other abnormality occurring in the interior will be encapsulated in such AuthenticationManager
                log.info("AuthenticationFailureServiceExceptionEvent: {} "  , request.getParameter("username"));
            }
        } catch (Exception ex) {
            log.error("on Authentication Failure Event", ex);
            throw ex;
        }
    }

    private void onAuthenticationSuccessEvent(AuthenticationSuccessEvent event) {
        log.info("start function onAuthenticationSuccessEvent");
        try {
                if(request.getParameter("username") != null){
                    String username = request.getParameter("username");
                    UserAuthDto userAuthDto = (UserAuthDto) event.getAuthentication().getPrincipal();

                    UsernamePasswordAuthenticationToken authentication =
                            (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
                    User user = (User) authentication.getPrincipal();
                    String clientId = user.getUsername();

                    log.info("user auth dto :: {}", userAuthDto);

                    if (userAuthDto.getUserId()!=0){
                        log.info("Authentication success clientId, {}, username, {}, IP Address : {}", clientId, username, ipAddressHandler.getClientIP());
                        userService.loginSuccess(username,  clientId);
                    }
                }
        } catch (Exception ex) {
            log.error("Authentication Success Event :: {}", ex.getMessage());
        }

    }

}
