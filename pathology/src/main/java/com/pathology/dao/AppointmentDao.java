package com.pathology.dao;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Appointment_details")
public class AppointmentDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String purpose;
    private String number;
    private String department;
    private String appointmentDate;
    private String appointmentTime;
}
