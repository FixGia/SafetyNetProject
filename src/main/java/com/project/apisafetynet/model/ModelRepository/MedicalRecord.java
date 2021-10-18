package com.project.apisafetynet.model.ModelRepository;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name="medicalrecord")
public class MedicalRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String birthdate;
    @ManyToMany
    private List<Medications> medications;
    @ManyToMany
    private List<Allergies> allergies;



    public MedicalRecord(String firstname, String lastname, String birthdate, List<Medications> medications, List<Allergies> allergies, Long id) {
        this.firstName = firstname;
        this.id = id;
        this.lastName = lastname;
        this.birthdate = birthdate;
        this.medications = medications;
        this.allergies = allergies;
    }

    public MedicalRecord() {
    }

    public MedicalRecord firstname (String firstname) {
        this.firstName = firstname;
        return this;
    }

    public MedicalRecord lastname( String lastname) {
        this.lastName = lastname;
        return this;
    }
    public MedicalRecord birthdate (String birthdate) {
        this.birthdate= birthdate;
        return this;
    }
    public MedicalRecord medications (List<Medications> medications){
        this.medications=medications;
        return this;
    }
    public MedicalRecord allergies(List<Allergies> allergies) {
        this.allergies=allergies;
        return this;
    }

    public MedicalRecord build () {
        return new MedicalRecord(firstName, lastName,birthdate,medications,allergies,id);
    }
}