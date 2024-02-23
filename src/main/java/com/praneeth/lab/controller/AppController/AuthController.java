package com.praneeth.lab.controller.AppController;

import com.praneeth.lab.config.security.custom.CustomUserAuthenticator;
import com.praneeth.lab.dto.common.CommonResponse;
import com.praneeth.lab.service.authService.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.praneeth.lab.constants.AppConstants.DetailConstants.HEADER_AUTH;




@RestController
@RequestMapping(value = "/auth")
@RequiredArgsConstructor
@Log4j2
public class AuthController {


    private final UserService userService;


    @PostMapping(value = "/logout", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> donorLogout(@RequestHeader(HEADER_AUTH) String token) {
        String role = CustomUserAuthenticator.getUserRoleFromToken(token);
        Long user_id = CustomUserAuthenticator.getUserIdFromToken(token);
        userService.logout(user_id, role);
        return ResponseEntity.ok(new CommonResponse<>(true, "You have been successfully logged out!"));
    }

}
