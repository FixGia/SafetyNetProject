package com.project.apisafetynet.model;

import com.project.apisafetynet.model.ModelRepository.Allergies;
import com.project.apisafetynet.model.ModelRepository.Medications;
import lombok.Data;
import java.util.List;

@Data
public class FloodMembers {

    private String firstName;
    private String lastName;
    private String phone;
    private int age;
    private List<Medications> medications;
    private List<Allergies> allergies ;


}
