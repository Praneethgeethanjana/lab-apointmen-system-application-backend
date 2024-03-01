package com.praneeth.lab.repository;

import com.praneeth.lab.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {


    @Query(value = "SELECT * FROM appointment a " +
            "INNER JOIN user u " +
            "WHERE " +
            "IF(?1 is not null , (u.first_name LIKE '%?1%' OR u.user_unique_id LIKE '%?1%' AND u.mobile LIKE '%?1%'), true) AND " +
            "IF(?2 is not null , a.status=?2, true) AND (DATE(a.appointment_date) BETWEEN DATE(?3) AND DATE(?4)) ORDER BY a.created ASC", nativeQuery = true)
    List<Appointment> filterAppointmentForAdmin(String keyword, String status, Date from, Date to);

    @Query(value = "SELECT * FROM appointment a " +
            "INNER JOIN user u " +
            "WHERE u.id=?1 AND " +
            "IF(?2 is not null , a.status=?2, true) AND (DATE(a.appointment_date) BETWEEN DATE(?3) AND DATE(?4)) ORDER BY a.created DESC", nativeQuery = true)
    List<Appointment> filterAppointmentForUser(Long userId,String status, Date from, Date to);

}
