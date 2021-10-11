package com.project.apisafetynet.model.ModelRepository;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name="medicalrecord")
public class MedicalRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String firstname;
    public String lastname;
    public String birthdate;
    public String medications;
    public String allergies;



    public MedicalRecord(String firstname, String lastname, String birthdate, String medications, String allergies, Long id) {
        this.firstname = firstname;
        this.id = id;
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
    public MedicalRecord allergies( String allergies) {
        this.allergies=allergies;
        return this;
    }

    public MedicalRecord build () {
        return new MedicalRecord(firstname,lastname,birthdate,medications,allergies, id);
    }
}