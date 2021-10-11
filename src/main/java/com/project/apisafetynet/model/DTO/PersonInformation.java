package com.project.apisafetynet.model.DTO;

import com.project.apisafetynet.model.ModelRepository.Allergies;
import com.project.apisafetynet.model.ModelRepository.Medications;
import lombok.Data;

import java.util.List;


@Data
public class PersonInformation {

    private String firstName;
    private String lastName;
    private String address;
    private int age;
    private String email;
    private List<Medications> medications;
    private List<Allergies> allergies;



    }

