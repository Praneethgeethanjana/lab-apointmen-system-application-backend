package com.praneeth.lab.repository;

import com.praneeth.lab.entity.AppointmentDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AppointmentDetailsRepository extends JpaRepository<AppointmentDetails, Long> {

}
