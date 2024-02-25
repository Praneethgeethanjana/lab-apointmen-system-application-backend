package com.praneeth.lab.controller.UserController;

import com.praneeth.lab.config.throttling_config.Throttling;
import com.praneeth.lab.dto.common.CommonResponse;
import com.praneeth.lab.dto.user.PublicUserReqDto;
import com.praneeth.lab.service.PublicUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/public-user")
@RequiredArgsConstructor
@Log4j2
public class PublicUserController {

    private final PublicUserService userService;

    @PostMapping(value = "/signup", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> donorLogout(@RequestBody PublicUserReqDto reqDto) {
        userService.userSignUp(reqDto);
        return ResponseEntity.ok(new CommonResponse<>(true, "You have been successfully logged out!"));
    }

    @Throttling(timeFrameInSeconds = 60, calls = 10)
    @PostMapping(value = "/account/verify")
    public ResponseEntity<?> verifyAccount(@RequestParam("token") String token) {
        log.info("verifyAccount token => reqBody: {}",token);
        userService.verifyAccount(token);
        return ResponseEntity.ok(new CommonResponse<>(true, "Your account has been activated successfully!"));
    }


}
