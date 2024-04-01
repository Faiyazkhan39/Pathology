package com.pathology.service;

import com.pathology.dao.*;
import com.pathology.model.AppointmentUser;
import com.pathology.model.ContactUsUser;
import com.pathology.model.RegistrationUser;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserRegistrationService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ContactUsRepo userContactRepo;

    @Autowired
    private AppointmentRepo appointmentRepo;
    @PersistenceContext
    private EntityManager entityManager;
    public String registerUser(RegistrationUser registrationUser){
        PathologyUser pathologyUserObj = userRepo.findByEmailId(registrationUser.getEmailId());
        if(pathologyUserObj!=null){
            return "already";
        }else{
            PathologyUser pathologyUser = new PathologyUser();
            pathologyUser.setEmailId(registrationUser.getEmailId());
            pathologyUser.setName(registrationUser.getName());
            pathologyUser.setPassword(registrationUser.getPassword());
            pathologyUser.setLastName(registrationUser.getLastName());
            userRepo.save(pathologyUser);
            return "success";
        }
    }
    public Boolean findByEmailAndPassword(String emailId, String password) {
        List<PathologyUser> users = entityManager.createQuery(
                        "SELECT u FROM PathologyUser u WHERE u.emailId = :email AND u.password = :password", PathologyUser.class)
                .setParameter("email", emailId)
                .setParameter("password", password)
                .getResultList();
        return !users.isEmpty();
    }
    public String getUserByGmail(String emailId){
        List<PathologyUser> users = entityManager.createQuery(
                        "SELECT u FROM PathologyUser u WHERE u.emailId = :email ", PathologyUser.class)
                .setParameter("email", emailId)
                .getResultList();
        return users.get(0).getName();
    }

    public String contactUsUser(ContactUsUser contactUsUser) {
        ContactUsUserDao contactUsUserDao = new ContactUsUserDao();
        contactUsUserDao.setEmail(contactUsUser.getEmail());
        contactUsUserDao.setName(contactUsUser.getName());
        contactUsUserDao.setSubject(contactUsUser.getSubject());
        contactUsUserDao.setMessage(contactUsUser.getMessage());
        contactUsUserDao.setNumber(contactUsUser.getNumber());
        contactUsUserDao.setCurrentTimeStamp(Timestamp.from(Instant.now()));
        userContactRepo.save(contactUsUserDao);
        return "true";
    }

    public String appointmentUser(AppointmentUser appointmentUser) {
        if(appointmentRepo.existsByEmailAndDepartmentAndAppointmentDateAndAppointmentTime(appointmentUser.getEmail(),appointmentUser.getDepartment(),appointmentUser.getAppointmentDate(),appointmentUser.getAppointmentTime())){
            return "already";
        }
        AppointmentDao appointmentDao = new AppointmentDao();
        appointmentDao.setName(appointmentUser.getName());
        appointmentDao.setEmail(appointmentUser.getEmail());
        appointmentDao.setNumber(appointmentUser.getNumber());
        appointmentDao.setDepartment(appointmentUser.getDepartment());
        appointmentDao.setPurpose(appointmentUser.getPurpose());
        appointmentDao.setAppointmentDate(appointmentUser.getAppointmentDate());
        appointmentDao.setAppointmentTime(appointmentUser.getAppointmentTime());
        appointmentRepo.save(appointmentDao);
        return "true";
    }
    public List<AppointmentUser> getUserAppointment(String userEmail){
        List<AppointmentDao> apo = appointmentRepo.findByEmail(userEmail);
        List<AppointmentUser> appUser = new ArrayList<>();
        for(AppointmentDao appointmentDao : apo){
            AppointmentUser app = new AppointmentUser();
            app.setName(appointmentDao.getName());
            app.setNumber(appointmentDao.getNumber());
            app.setPurpose(appointmentDao.getPurpose());
            app.setEmail(appointmentDao.getEmail());
            app.setDepartment(appointmentDao.getDepartment());
            app.setAppointmentDate(appointmentDao.getAppointmentDate());
            app.setAppointmentTime(appointmentDao.getAppointmentTime());
            appUser.add(app);
        }
        return appUser;
    }
}
