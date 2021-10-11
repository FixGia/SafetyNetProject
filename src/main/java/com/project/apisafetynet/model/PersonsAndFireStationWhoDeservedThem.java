package com.project.apisafetynet.model;

import com.project.apisafetynet.model.ModelRepository.Allergies;
import com.project.apisafetynet.model.ModelRepository.Medications;
import lombok.Data;

import java.util.List;

@Data
public class PersonsAndFireStationWhoDeservedThem {

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private int age;
    private List<Medications> medications;
    private List<Allergies> allergies;
    private String fireStationNumber;

}

