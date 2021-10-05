package com.project.apisafetynet.model;

import lombok.Data;


@Data
public class PersonInformation {

    private String firstName;
    private String lastName;
    private String address;
    private int age;
    private String email;
    private String medications;
    private String allergies;

    @Data
    public static class Age {

        private String birthdate;
        private String pattern;

        public Age(String birthdate, String pattern) {
            this.birthdate = birthdate;
            this.pattern = pattern;
        }

    }
}
