package com.pathology.model;

import lombok.Data;

import java.math.BigInteger;

@Data
public class ContactUsUser {
    private String name;
    private String email;
    private String subject;
    private BigInteger number;
    private String message;

}
