package com.pathology.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepo extends JpaRepository<AppointmentDao,Long> {
    boolean existsByEmailAndDepartmentAndAppointmentDateAndAppointmentTime(String email, String department, String appointmentDate, String appointmentTime);
    List<AppointmentDao> findByEmail(String email);;
}