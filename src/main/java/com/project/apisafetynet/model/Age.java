package com.project.apisafetynet.model;

import lombok.Data;

@Data
public class Age {
    private String birthdate;
    private String pattern;

    public Age(String birthdate, String pattern) {
        this.birthdate = birthdate;
        this.pattern = pattern;

    }
}