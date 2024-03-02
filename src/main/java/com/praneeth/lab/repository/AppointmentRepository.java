package com.praneeth.lab.repository;

import com.praneeth.lab.dto.appointment.AppointmentReportResDto;
import com.praneeth.lab.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {


    @Query(value = "SELECT * FROM appointment a " +
            "INNER JOIN user u ON a.user_id = u.id " +
            "WHERE " +
            "IF(?1 is not null , (u.first_name LIKE %?1%) OR (u.user_unique_id LIKE %?1%) OR (u.mobile LIKE %?1%), true) AND " +
            "IF(?2 is not null , a.status=?2, true) AND (DATE(a.appointment_date) BETWEEN DATE(?3) AND DATE(?4)) ORDER BY a.created ASC", nativeQuery = true)
    List<Appointment> filterAppointmentForAdmin(String keyword, String status, Date from, Date to);

    @Query(value = "SELECT * FROM appointment a " +
            "INNER JOIN user u ON a.user_id = u.id " +
            "WHERE u.id=?1 AND " +
            "IF(?2 is not null , a.status=?2, true) AND (DATE(a.appointment_date) BETWEEN DATE(?3) AND DATE(?4)) ORDER BY a.created DESC", nativeQuery = true)
    List<Appointment> filterAppointmentForUser(Long userId,String status, Date from, Date to);

    @Query(value = "SELECT new com.praneeth.lab.dto.appointment.AppointmentReportResDto(a.id, a.status, a.appointmentDate, a.created, a.paymentSlipUrl, a.total, ad.fee, mt.testName, ad.reportUrl, ad.note,u.userName, u.firstName, u.lastName, u.userUniqueId) FROM Appointment a " +
            "INNER JOIN User u ON a.user=u " +
            "INNER JOIN AppointmentDetails ad ON ad.appointment=a " +
            "INNER JOIN MedicalTest mt ON ad.medicalTest=mt " +
            "WHERE (?1 IS NULL OR a.id=?1) " +
            "ORDER BY a.created ASC", nativeQuery = false)
    List<AppointmentReportResDto> getAppointmentReport(Long appointmentId);

    @Query(value = "SELECT new com.praneeth.lab.dto.appointment.AppointmentReportResDto(a.id, a.status, a.appointmentDate, a.created, a.paymentSlipUrl, a.total, ad.fee, mt.testName, ad.reportUrl, ad.note,u.userName, u.firstName, u.lastName, u.userUniqueId) FROM Appointment a " +
            "INNER JOIN User u ON a.user=u " +
            "INNER JOIN AppointmentDetails ad ON ad.appointment=a " +
            "INNER JOIN MedicalTest mt ON ad.medicalTest=mt " +
            "WHERE (?1 IS NULL OR a.id=?1) AND u.id=?2 " +
            "ORDER BY a.created ASC", nativeQuery = false)
    List<AppointmentReportResDto> getAppointmentReportForUser(Long appointmentId, Long userId);

}
