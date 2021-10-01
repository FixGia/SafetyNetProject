package com.project.apisafetynet.model;

import lombok.Data;

@Data
public class FamilyMembers{

    private String firstName;
    private String lastName;


    public FamilyMembers(String firstName, String lastName) {
        this.firstName=firstName;
        this.lastName=lastName;

    }
}
