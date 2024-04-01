package com.pathology.model;

import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Date;
import java.util.List;

@Data
public class AppointmentUser {
    private String name;
    private String email;
    private String purpose;
    private String number;
    private String department;
    private String appointmentDate;
    private String appointmentTime;
}
