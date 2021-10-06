package com.project.apisafetynet.model;

import com.project.apisafetynet.model.ModelRepository.Allergies;
import lombok.Data;

import java.util.List;


@Data
public class PersonInformation {

    private String firstName;
    private String lastName;
    private String address;
    private int age;
    private String email;
    private String medications;
    private List<Allergies> allergies;

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
