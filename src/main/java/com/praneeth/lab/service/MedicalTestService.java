package com.praneeth.lab.service;

import com.praneeth.lab.dto.medicaltest.MedicalTestResDto;

import java.util.List;

public interface MedicalTestService {
    List<MedicalTestResDto> getAllMedicalTest();
}
