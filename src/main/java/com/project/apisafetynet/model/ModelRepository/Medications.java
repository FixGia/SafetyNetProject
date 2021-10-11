package com.project.apisafetynet.model.ModelRepository;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name="Medications")
public class Medications {

    @Id
    @Column
    private String nameMedications;

    public Medications() {

    }

    public Medications nameMedications(String nameMedications){
        this.nameMedications = nameMedications;
        return this;
    }

}
