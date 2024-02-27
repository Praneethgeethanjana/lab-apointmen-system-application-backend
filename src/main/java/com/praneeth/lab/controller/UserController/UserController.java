package com.praneeth.lab.controller.UserController;

import com.praneeth.lab.dto.common.CommonResponse;
import com.praneeth.lab.dto.medicaltest.MedicalTestResDto;
import com.praneeth.lab.service.MedicalTestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/user")
@RequiredArgsConstructor
@Log4j2
public class UserController {
    private final MedicalTestService medicalTestService;
    @GetMapping(value = "/medical-test", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getMedicalTest() {
        List<MedicalTestResDto> res = medicalTestService.getAllMedicalTest();
        return ResponseEntity.ok(new CommonResponse<>(true,  res));
    }
}
