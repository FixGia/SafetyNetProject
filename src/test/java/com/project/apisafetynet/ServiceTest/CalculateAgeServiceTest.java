package com.project.apisafetynet.ServiceTest;

import com.project.apisafetynet.Repository.MedicalRecordRepository;
import com.project.apisafetynet.Service.CalculateAgeService;
import com.project.apisafetynet.Service.CalculateAgeServiceImpl;

import com.project.apisafetynet.model.DTO.Age;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;

import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
public class CalculateAgeServiceTest {


    private static CalculateAgeService calculateAgeService;



    @BeforeAll
        public static void setUp() {
        calculateAgeService = new CalculateAgeServiceImpl();
    }

    @Test
    public void CalculateAgeTest() {
        Age age = new Age("12/03/1991","MM/dd/yyyy");
        calculateAgeService.CalculateAge(age);
        assertEquals(calculateAgeService.CalculateAge(age), 29);

    }
}
