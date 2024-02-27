package com.praneeth.lab.repository;

import com.praneeth.lab.entity.MedicalTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface MedicalTestRepository extends JpaRepository<MedicalTest, Long> {

}
