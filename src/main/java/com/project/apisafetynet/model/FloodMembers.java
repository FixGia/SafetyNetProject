package com.project.apisafetynet.model;

import com.project.apisafetynet.model.ModelRepository.Allergies;
import lombok.Data;
import java.util.List;

@Data
public class FloodMembers {

    private String firstName;
    private String lastName;
    private String phone;
    private int age;
    private String medications;
    private List<Allergies> allergies ;


}
