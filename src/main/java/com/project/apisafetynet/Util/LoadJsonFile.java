package com.project.apisafetynet.Util;
import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;
import com.project.apisafetynet.Service.*;
import com.project.apisafetynet.model.ModelRepository.*;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Data
@Service
@Log4j2
public class LoadJsonFile {

    private static String filePath = Thread.currentThread().getContextClassLoader().getResource("data.json").getFile();
    private static byte[] bytesFile;
    private final PersonService personService;
    private final FireStationService fireStationService;
    private final MedicalRecordService medicalRecordService;
    private final AllergiesService allergiesService;
    private final MedicationService medicationService;
    private MedicalRecord medicalRecord;

    public LoadJsonFile(PersonService personService, FireStationService fireStationService, MedicalRecordService medicalRecordService, AllergiesService allergiesService, MedicationService medicationService) {
        this.personService = personService;
        this.fireStationService = fireStationService;
        this.medicalRecordService = medicalRecordService;
        this.allergiesService = allergiesService;
        this.medicationService = medicationService;
    }


    public void readPersons() throws IOException {
        bytesFile = Files.readAllBytes(new File(filePath).toPath());
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
        personService.SavePersons(persons);
        persons.forEach(p -> log.info(p.firstName.concat(p.lastName).concat(p.address).concat(p.city).concat(p.phone).concat(p.zip)));



    }


    public void readFireStation() throws IOException {
        bytesFile = Files.readAllBytes(new File(filePath).toPath());
        JsonIterator iter = JsonIterator.parse(bytesFile);
        Any any = iter.readAny();
        Any fireStationAny = any.get("firestations");
        List<FireStation> firestations = new ArrayList<>();
        fireStationAny.forEach(a -> firestations.add(new FireStation().address(a.get("address").toString()).station(a.get("station").toString())
                .build()));
        firestations.forEach(f -> log.info(f.address.concat("  "+f.station)));
        fireStationService.saveFireStations(firestations);


    }
    public void readMedicalRecords() throws IOException {

        bytesFile = Files.readAllBytes(new File(filePath).toPath());
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
        medicalRecords.forEach(m -> log.info(m.getFirstName().concat(m.getLastName()).concat(m.getBirthdate()).concat(String.valueOf(m.getMedications())).concat(String.valueOf(m.getAllergies()))));
        medicalRecordService.saveMedicalRecord(medicalRecords);

            }

        }




