package com.praneeth.lab.service.impl;

import com.praneeth.lab.dto.appointment.AppointmentCreateDto;
import com.praneeth.lab.entity.Appointment;
import com.praneeth.lab.entity.AppointmentDetails;
import com.praneeth.lab.entity.MedicalTest;
import com.praneeth.lab.entity.User;
import com.praneeth.lab.exception.dto.CustomServiceException;
import com.praneeth.lab.repository.AppointmentDetailsRepository;
import com.praneeth.lab.repository.AppointmentRepository;
import com.praneeth.lab.repository.MedicalTestRepository;
import com.praneeth.lab.repository.UserRepository;
import com.praneeth.lab.service.AppointmentService;
import com.praneeth.lab.utilities.AWSHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.*;

import static com.praneeth.lab.constants.AppConstants.ErrorConstants.S3_FAILED_TO_SAVE_FILE;
import static com.praneeth.lab.constants.S3BucketFolderConstant.DOCTOR_RECEIPT_PATH;
import static com.praneeth.lab.constants.S3BucketFolderConstant.PAYMENT_PATH;
import static com.praneeth.lab.exception.constants.ErrorCodeConstants.RESOURCE_NOT_FOUND;
import static com.praneeth.lab.exception.constants.ErrorCodeConstants.SYSTEM_ERROR;

@Log4j2
@RequiredArgsConstructor
@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final ModelMapper modelMapper;
    private final AWSHandler awsHandler;
    private final UserRepository userRepository;
    private final AppointmentRepository appointmentRepository;
    private final MedicalTestRepository medicalTestRepository;
    private final AppointmentDetailsRepository appointmentDetailsRepository;
    @Override
    @Transactional
    public void saveAppointment(AppointmentCreateDto dto, Long userId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new CustomServiceException(RESOURCE_NOT_FOUND, "User not found"));
        String doctorReceiptUrl = null;
        String paymentSlip = null;

        if (dto.getDoctorReceiptUrl()!=null && !dto.getDoctorReceiptUrl().isEmpty()){
            doctorReceiptUrl = awsHandler.uploadToS3Bucket(dto.getDoctorReceiptUrl(), (user.getUserName()+"-" + UUID.randomUUID()).replaceAll("[-/+\\s^%@<>!#*.,~$\\\\]", "-"), DOCTOR_RECEIPT_PATH)
                    .orElseThrow(() -> new CustomServiceException(SYSTEM_ERROR, S3_FAILED_TO_SAVE_FILE));
        }

        if (dto.getPaymentSlipUrl()!=null && !dto.getPaymentSlipUrl().isEmpty()){
             paymentSlip = awsHandler.uploadToS3Bucket(dto.getPaymentSlipUrl(), (user.getUserName()+"-" + UUID.randomUUID()).replaceAll("[-/+\\s^%@<>!#*.,~$\\\\]", "-"), PAYMENT_PATH)
                    .orElseThrow(() -> new CustomServiceException(SYSTEM_ERROR, S3_FAILED_TO_SAVE_FILE));
        }else {
            throw new CustomServiceException(SYSTEM_ERROR, "Please upload payment slip");
        }


        String[] idList = dto.getTestIdList().trim().split(",");

        Appointment appointment = Appointment.builder()
                .appointmentDate(new Date())
                .total(BigDecimal.ZERO)
                .doctorReceiptUrl(doctorReceiptUrl)
                .paymentSlipUrl(paymentSlip)
                .created(new Date())
                .appointmentDate(dto.getAppointmentDate())
                .build();

        Appointment saveAppointment = appointmentRepository.save(appointment);

        LinkedList<AppointmentDetails> appointmentDetails = new LinkedList<>();

        BigDecimal total = BigDecimal.ZERO;
        for (String id : idList) {
            MedicalTest medicalTest = medicalTestRepository.findById(Long.valueOf(id)).orElseThrow(() -> new CustomServiceException(RESOURCE_NOT_FOUND, "Medical test not found!"));
            AppointmentDetails details = AppointmentDetails.builder()
                    .medicalTest(medicalTest)
                    .fee(medicalTest.getFee())
                    .created(new Date())
                    .appointment(saveAppointment)
                    .build();
            appointmentDetails.add(details);
            total=total.add(medicalTest.getFee());
        }

        appointmentDetailsRepository.saveAll(appointmentDetails);

        saveAppointment.setTotal(total);
        appointmentRepository.save(saveAppointment);

    }
}