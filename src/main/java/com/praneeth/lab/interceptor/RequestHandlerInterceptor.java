package com.praneeth.lab.interceptor;
import com.praneeth.lab.config.security.custom.CustomUserAuthenticator;
import com.praneeth.lab.exception.dto.CustomAuthenticationException;
import com.praneeth.lab.service.authService.UserService;
import com.praneeth.lab.constants.AppConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;


@Component
@RequiredArgsConstructor
@Log4j2
public class RequestHandlerInterceptor implements HandlerInterceptor {


    private final UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        try {
            String token = request.getHeader(AppConstants.DetailConstants.HEADER_AUTH);

            if (token!=null){
                String role = CustomUserAuthenticator.getUserRoleFromToken(token);
                Long user_id = CustomUserAuthenticator.getUserIdFromToken(token);
                Date lastLogOutTime = CustomUserAuthenticator.getLastLogOutTimeFromToken(token);
                log.info("start Interceptor => preHandle  user_id : {}, role : {}, last logout time{}",user_id, role, lastLogOutTime);

                Date lastLogOutDateTime = userService.getLastLogOutTime(user_id, role);
                if(lastLogOutDateTime.compareTo(lastLogOutTime)!=0) throw new CustomAuthenticationException(401,"Invalid token");
                return true;
            }
            return false;

        } catch (Exception e) {
            log.error("Function : token preHandle " + e.getMessage(), e);
            throw new CustomAuthenticationException(401,"Invalid token");
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        log.info("Handler execution is complete");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        log.info("Request and Response is completed");
    }
}
