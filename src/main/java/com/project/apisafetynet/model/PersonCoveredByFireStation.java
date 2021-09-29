package com.project.apisafetynet.model;

import lombok.Data;

@Data
public class PersonCoveredByFireStation {

    private String Id;
    private String firstName;
    private String lastName;
    private String address;
    private String phone;
    private String birthDate;

    public String getId() {
        return firstName+","+lastName;
    }
    public void setId(String id) {
        Id = id;
    }
}
