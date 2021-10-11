package com.project.apisafetynet.model.ModelRepository;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="Persons")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;
    @Column
    public String firstName;
    @Column
    public String lastName;
    @Column
    public String address;
    @Column
    public String city;
    @Column
    public String zip;
    @Column
    public String phone;
    @Column
    public String email;


    public Person(String firstName, String lastName, String address, String city, String zip, String phone, String email, Long id) {
        this.firstName = firstName;
        this.Id = id;
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
      return new Person(firstName,lastName,address,city,zip,phone,email,Id);
    }


}


