package com.project.apisafetynet.model.ModelRepository;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Medications {
    @Id
    @Column(name = "name_medication", nullable = false)
    private String nameMedication;

    public Medications() {

    }

    public Medications nameMedication(String nameMedication){
        this.nameMedication = nameMedication;
        return this;
    }
}
