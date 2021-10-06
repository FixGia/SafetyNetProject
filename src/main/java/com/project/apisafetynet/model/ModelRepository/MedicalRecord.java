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
    private String id;
    private String firstname;
    private String lastname;
    private String birthdate;
    private String medications;

    @OneToMany
    private List<Allergies> allergies;




    public MedicalRecord(String firstname, String lastname, String birthdate, String medications, List<Allergies> allergies) {
        this.id=firstname+","+lastname;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthdate = birthdate;
        this.medications = medications;
        this.allergies = allergies;
    }

    public MedicalRecord() {
    }

    public MedicalRecord firstname (String firstname) {
        this.firstname = firstname;
        return this;
    }

    public MedicalRecord lastname( String lastname) {
        this.lastname = lastname;
        return this;
    }
    public MedicalRecord birthdate (String birthdate) {
        this.birthdate= birthdate;
        return this;
    }
    public MedicalRecord medications (String medications){
        this.medications=medications;
        return this;
    }
    public MedicalRecord allergies(List<Allergies> allergies) {
        this.allergies=allergies;
        return this;
    }

    public MedicalRecord build () {
        return new MedicalRecord(firstname,lastname,birthdate,medications,allergies);
    }
}