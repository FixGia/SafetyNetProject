package com.project.apisafetynet.Service.Impl;
import com.project.apisafetynet.Service.CalculateAgeService;
import com.project.apisafetynet.model.DTO.Age;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;


@Service
public class CalculateAgeServiceImpl implements CalculateAgeService {



    public int CalculateAge(Age age) {

        String pattern = age.getPattern();
        String birthDate = age.getBirthdate();
            LocalDate actualDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            LocalDate birthdateLocalDate = LocalDate.parse(birthDate, formatter);
            long ageInNumberOfDays = ChronoUnit.DAYS.between(birthdateLocalDate, actualDate);
            return (int)((int) ageInNumberOfDays / 365.25);

    }

}

