package com.praneeth.lab.service.impl;

import com.praneeth.lab.dto.medicaltest.MedicalTestResDto;
import com.praneeth.lab.repository.MedicalTestRepository;
import com.praneeth.lab.service.MedicalTestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor
@Service
public class MedicalTestServiceImpl implements MedicalTestService {

    private final MedicalTestRepository medicalTestRepository;
    private final ModelMapper modelMapper;
    @Override
    public List<MedicalTestResDto> getAllMedicalTest() {
        return medicalTestRepository.findAll()
                .stream()
                .map(medicalTest -> modelMapper.map(medicalTest, MedicalTestResDto.class))
                .collect(Collectors.toList());
    }
}
