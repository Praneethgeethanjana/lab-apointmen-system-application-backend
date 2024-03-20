package com.praneeth.lab.service.impl;

import com.praneeth.lab.constants.EmailHtmlConstant;
import com.praneeth.lab.dto.appointment.AppointmentCreateDto;
import com.praneeth.lab.dto.appointment.AppointmentReportResDto;
import com.praneeth.lab.dto.appointment.AppointmentResDto;
import com.praneeth.lab.entity.Appointment;
import com.praneeth.lab.entity.AppointmentDetails;
import com.praneeth.lab.entity.MedicalTest;
import com.praneeth.lab.entity.User;
import com.praneeth.lab.enums.common.Status;
import com.praneeth.lab.exception.dto.CustomServiceException;
import com.praneeth.lab.repository.AppointmentDetailsRepository;
import com.praneeth.lab.repository.AppointmentRepository;
import com.praneeth.lab.repository.MedicalTestRepository;
import com.praneeth.lab.repository.UserRepository;
import com.praneeth.lab.service.AppointmentService;
import com.praneeth.lab.utilities.AWSHandler;
import com.praneeth.lab.utilities.EmailSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static com.praneeth.lab.constants.AppConstants.Email.*;
import static com.praneeth.lab.constants.AppConstants.ErrorConstants.S3_FAILED_TO_SAVE_FILE;
import static com.praneeth.lab.constants.S3BucketFolderConstant.*;
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
    private final EmailSender emailSender;
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
                .appointmentDate(dto.getAppointmentDate())
                .total(BigDecimal.ZERO)
                .doctorReceiptUrl(doctorReceiptUrl)
                .paymentSlipUrl(paymentSlip)
                .status(Status.PENDING)
                .remark(dto.getRemark())
                .created(new Date())
                .user(user)
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

    @Override
    public List<AppointmentResDto> filterAppointmentForAdmin(Date fromDate, Date toDate, String keyword, Status status) {

        String updateKeyword = (keyword == null || keyword.isEmpty()) ? null : keyword.trim();
        String updatedStatus = status.equals(Status.ALL) ? null : status.name();
        return appointmentRepository.filterAppointmentForAdmin(updateKeyword, updatedStatus, fromDate, toDate)
                .stream()
                .map(appointment -> modelMapper.map(appointment, AppointmentResDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<AppointmentResDto> filterAppointmentForUser(Long userId, Date fromDate, Date toDate, Status status) {
        String updatedStatus = status.equals(Status.ALL) ? null : status.name();
        return appointmentRepository.filterAppointmentForUser(userId, updatedStatus, fromDate, toDate)
                .stream()
                .map(appointment -> modelMapper.map(appointment, AppointmentResDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void changeAppointmentStatus(Long appointmentId, Status status) {
        log.info("start method => changeAppointmentStatus");
        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow(() -> new CustomServiceException(RESOURCE_NOT_FOUND, "Appointment not found!"));
        if (status==appointment.getStatus()){
            throw new CustomServiceException(SYSTEM_ERROR, "This appointment is already in "+status.name().toLowerCase() +" status");
        }

        if (status==Status.COMPLETED){
            boolean b = appointment.getAppointmentDetails()
                    .stream()
                    .anyMatch(appointmentDetails -> appointmentDetails.getReportUrl() == null);

            if (b) throw new CustomServiceException("No document has been uploaded in the report regarding this appointment.");
        }

        if (status==Status.ACTIVE){
                try {
                    emailSender.sendSimpleEmail(appointment.getUser().getUserName(), APPOINTMENT_ACTIVE_EMAIL,
                            EmailHtmlConstant.sendAppointmentApprovedEmail(appointment));
                } catch (MessagingException e) {
                    throw new CustomServiceException(e.getMessage());
                }
        }

        appointment.setStatus(status);
        appointmentRepository.save(appointment);
    }

    @Override
    public void uploadReportToAppointment(Long appointmentDetailsId, MultipartFile report, String note) {
        AppointmentDetails appointmentDetails = appointmentDetailsRepository.findById(appointmentDetailsId).orElseThrow(() -> new CustomServiceException(RESOURCE_NOT_FOUND, "related appointment details not found!"));

        String reportUrl = null;
        if (report!=null && !report.isEmpty()){
            reportUrl = awsHandler.uploadToS3Bucket(report, UUID.randomUUID().toString().replaceAll("[-/+\\s^%@<>!#*.,~$\\\\]", "-"), REPORT_PATH)
                    .orElseThrow(() -> new CustomServiceException(SYSTEM_ERROR, S3_FAILED_TO_SAVE_FILE));
        }else {
            throw new CustomServiceException(SYSTEM_ERROR, "Please upload the report");
        }

        appointmentDetails.setReportUrl(reportUrl);
        appointmentDetails.setNote(note);

        Appointment appointment = appointmentDetails.getAppointment();
       /* appointment.setStatus(Status.COMPLETED);
        appointmentRepository.save(appointment);*/
        appointmentDetailsRepository.save(appointmentDetails);
    }

    @Override
    public List<AppointmentReportResDto> getAppointmentReportForAdmin(Long appointmentId) {
        Long updateAppointmentId = appointmentId == 0 ? null : appointmentId;
        return appointmentRepository.getAppointmentReport(updateAppointmentId);
    }

    @Override
    public List<AppointmentReportResDto> getAppointmentReportForUser(Long appointmentId, Long userId) {
        Long updateAppointmentId = appointmentId == 0 ? null : appointmentId;
        return appointmentRepository.getAppointmentReportForUser(updateAppointmentId, userId);
    }
}
