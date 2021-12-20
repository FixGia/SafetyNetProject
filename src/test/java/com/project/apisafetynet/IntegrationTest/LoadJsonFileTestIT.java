package com.project.apisafetynet.IntegrationTest;

import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;
import com.project.apisafetynet.Service.*;
import com.project.apisafetynet.Util.LoadJsonFile;
import com.project.apisafetynet.model.ModelRepository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class LoadJsonFileTestIT {

    private static byte[] bytesFile;
    private String filePath = Thread.currentThread().getContextClassLoader().getResource("Data.Json").getFile();

    @Autowired
    PersonService personService;
    @Autowired
    FireStationService fireStationService;
    @Autowired
    MedicationService medicationService;
    @Autowired
    AllergiesService allergiesService;
    @Autowired
    MedicalRecordService medicalRecordService;
    @Autowired
    LoadJsonFile loadJsonFile;



    @Test
    public void testContext() {
    }

    @BeforeEach
    public void setUpBeforeEachTest() throws IOException {
        bytesFile = Files.readAllBytes(new File(filePath).toPath());

    }

    @Test
    public void ReadPersonsFromJsonFileTest() throws IOException {
        JsonIterator iter = JsonIterator.parse(bytesFile);
        Any any = iter.readAny();
        Any personAny = any.get("Persons");
        List<Person> persons = new ArrayList<>();
        personAny.forEach(a -> persons.add(new Person().firstName(a.get("firstName").toString())
                .address(a.get("address").toString())
                .city(a.get("city").toString())
                .lastName(a.get("lastName").toString())
                .phone(a.get("phone").toString())
                .zip(a.get("zip").toString())
                .email(a.get("email").toString())
                .build()));
        assertEquals(persons.size(), 23);
    }

    @Test
    public void ReadMedicalRecordsFromJsonFileTest() throws IOException{
        JsonIterator iter = JsonIterator.parse(bytesFile);
        Any any = iter.readAny();
        Any medicalrecordsAny = any.get("medicalrecords");
        List<MedicalRecord> medicalRecords = new ArrayList<>();
        medicalrecordsAny.forEach(mr -> {
            String allergiesAny = mr.get("allergies").toString().replaceAll("[\\[\\]\"]", "");
            List<String> allergiesAnyList = List.of(allergiesAny.split(","));
            List<Allergies> allergies = new ArrayList<>();
            allergiesAnyList.forEach(a->{
                if(!a.isEmpty()) allergies.add(new Allergies().nameAllergies(a));
            });
            allergiesService.saveAll(allergies);
            String medicationsAny = mr.get("medications").toString().replaceAll("[\\[\\]\"]", "");
            List<String> medicationsAnyList= List.of(medicationsAny.split(","));
            List<Medications> medications = new ArrayList<>();
            medicationsAnyList.forEach(a -> {
                if(!a.isEmpty()) medications.add(new Medications().nameMedication(a));
            });
            medicationService.saveAll(medications);
            medicalRecords.add(new MedicalRecord()
                    .firstname(mr.get("firstName").toString())
                    .lastname(mr.get("lastName").toString())
                    .birthdate(mr.get("birthdate").toString())
                    .medications(medications)
                    .allergies(allergies)
                    .build());
        });

        assertEquals(medicalRecords.size(), 23);
    }
    @Test
    public void ReadFireStationFromJsonFileTest() throws IOException {
        JsonIterator iter = JsonIterator.parse(bytesFile);
        Any any = iter.readAny();
        Any fireStationAny = any.get("firestations");
        List<FireStation> firestations = new ArrayList<>();
        fireStationAny.forEach(a -> firestations.add(new FireStation().address(a.get("address").toString()).station(a.get("station").toString())
                .build()));
        assertEquals(firestations.size(), 13);
    }
}
