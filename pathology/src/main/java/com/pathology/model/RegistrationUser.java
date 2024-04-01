package com.pathology.model;

import lombok.Data;

@Data
public class RegistrationUser {
    private String name;
    private String lastName;
    private String emailId;
    private String password;
    private String confirmPassword;
}
