package com.project.apisafetynet.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name= "medicalrecords")
public class MedicalRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    private String birthday;
    private String medications;
    private String allergies;
}
