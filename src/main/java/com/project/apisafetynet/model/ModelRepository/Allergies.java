package com.project.apisafetynet.model.ModelRepository;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Allergies {

    @Id
    @Column
    private String nameAllergies;

    public Allergies() {
    }


    public Allergies nameAllergies(String nameAllergies){
        this.nameAllergies = nameAllergies;
        return this;
    }

}
