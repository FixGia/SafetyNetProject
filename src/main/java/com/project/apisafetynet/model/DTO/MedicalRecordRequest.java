package com.project.apisafetynet.model.DTO;

import com.project.apisafetynet.model.ModelRepository.Allergies;
import com.project.apisafetynet.model.ModelRepository.Medications;
import lombok.Data;

import java.util.List;
@Data
public class MedicalRecordRequest {
    private Long id;
    private String firstName;
    private String lastName;
    private String birthdate;
    private List<Medications> medications;
    private List<Allergies> allergies;


}
