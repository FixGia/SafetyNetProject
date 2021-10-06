package com.project.apisafetynet.model.ModelRepository;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="allergies")
public class Allergies {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column
    private String nameAllergies;


    public Allergies() {

    }


    public Allergies nameAllergies(String nameAllergies){
        this.nameAllergies = nameAllergies;
        return this;
    }

}
