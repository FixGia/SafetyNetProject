package com.project.apisafetynet.model;

import lombok.Data;

@Data
public class PersonsAndFireStationWhoDeservedThem {

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private int age;
    private String medications;
    private String allergies;
    private String fireStationNumber;

}

