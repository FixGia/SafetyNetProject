package com.project.apisafetynet.model;

import lombok.Data;

@Data
public class PersonInfo {
    private String firstName;
    private String lastName;
    private String address;
    private String age;
    private String email;
    private String medications;
    private String allergies;
}
