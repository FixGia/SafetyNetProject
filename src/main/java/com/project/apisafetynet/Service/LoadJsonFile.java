package com.project.apisafetynet.Service;
import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;
import com.project.apisafetynet.model.ModelRepository.*;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Data
@Service
public class LoadJsonFile {

    private static String filePath = "src/main/resources/data.json";
    private static byte[] bytesFile;
    private static PersonService personService;
    private static FireStationServiceImp fireStationService;
    private static MedicalRecordServiceImpl medicalRecordService;
    private final AllergiesService allergiesService;
    private final MedicationsService medicationsService;

    public LoadJsonFile(MedicalRecordServiceImpl medicalRecordService, PersonService personService, FireStationServiceImp fireStationService, AllergiesService allergiesService, MedicationsService medicationsService) {
        LoadJsonFile.personService = personService;
        LoadJsonFile.fireStationService = fireStationService;
        LoadJsonFile.medicalRecordService = medicalRecordService;
        this.allergiesService = allergiesService;
        this.medicationsService = medicationsService;
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

        persons.forEach(p -> System.out.println(p.firstName.concat(p.lastName).concat(p.address).concat(p.city).concat(p.phone).concat(p.zip)));


    }


    public void readFireStation() throws IOException {

        bytesFile = Files.readAllBytes(new File(filePath).toPath());
        JsonIterator iter = JsonIterator.parse(bytesFile);
        Any any = iter.readAny();
        Any fireStationAny = any.get("firestations");
        List<FireStation> firestations = new ArrayList<>();
        fireStationAny.forEach(a -> firestations.add(new FireStation().address(a.get("address").toString()).station(a.get("station").toString())
                .build()));
        firestations.forEach(f -> System.out.println(f.address.concat("  " + f.station)));
        fireStationService.saveFireStations(firestations);


    }

    public void readMedicalRecords() throws IOException {
        bytesFile = Files.readAllBytes(new File(filePath).toPath());
        JsonIterator iter = JsonIterator.parse(bytesFile);
        Any any = iter.readAny();
        Any medicalrecordsAny = any.get("medicalrecords");
        List<MedicalRecord> medicalRecords = new ArrayList<>();
           /** List<Allergies> allergies = new ArrayList<>();
            medicalrecordsAny.forEach(a -> allergies.add(new Allergies().nameAllergies(a.get("allergies").toString())));
            allergiesService.saveAll(allergies);
            **/
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
            if(!a.isEmpty()) medications.add(new Medications().nameMedications(a));
        });
        medicationsService.saveAll(medications);
            medicalRecords.add(new MedicalRecord()
                    .firstname(mr.get("firstName").toString())
                    .lastname(mr.get("lastName").toString())
                    .birthdate(mr.get("birthdate").toString())
                    .medications(medications)
                    .allergies(allergies)
                    .build());
        });

        medicalRecordService.saveMedicalRecord(medicalRecords);

            }

        }




