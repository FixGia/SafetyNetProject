package com.project.apisafetynet.model.DTO;

import lombok.Data;


@Data
public class PersonRequest {

    public String firstName;
    public String lastName;
    public String address;
    public String city;
    public String zip;
    public String phone;
    public String email;

}
