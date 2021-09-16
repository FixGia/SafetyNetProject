package com.project.apisafetynet.model;

import com.jsoniter.annotation.JsonCreator;
import com.jsoniter.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="Persons")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;
    public String firstName;
    public String lastName;
    public String address;
    public String city;
    public String zip;
    public String phone;
    public String email;



    @JsonCreator
    public Person(
            @JsonProperty("firstname") String firstName,
            @JsonProperty("lastname") String lastName,
            @JsonProperty("address") String address,
            @JsonProperty("city")String city,
            @JsonProperty("zip")String zip,
            @JsonProperty("phone") String phone,
            @JsonProperty("email")String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address= address;
        this.city=city;
        this.zip=zip;
        this.phone=phone;
        this.email=email;

    }

    public Person() {

    }


    public Person firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }
    public Person lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public Person phone(String phone) {
        this.phone = phone;
        return this;
    }

    public Person zip(String zip) {
        this.zip = zip;
        return this;
    }

    public Person address(String address) {
        this.address = address;
        return this;
    }

    public Person city(String city) {
        this.city = city;
        return this;
    }

    public Person email(String email) {
        this.email = email;
        return this;
    }


    public Person build() {
      return new Person(firstName,lastName,address,city,zip,phone,email);
    }


}

