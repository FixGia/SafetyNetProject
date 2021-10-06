package com.project.apisafetynet.Service;
import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;
import com.project.apisafetynet.model.ModelRepository.Allergies;
import com.project.apisafetynet.model.ModelRepository.FireStation;
import com.project.apisafetynet.model.ModelRepository.MedicalRecord;
import com.project.apisafetynet.model.ModelRepository.Person;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@Service
public class LoadJsonFile {

    private static String filePath = "src/main/resources/data.json";
    private static byte[] bytesFile;
    private static PersonService personService;
    private static FireStationServiceImp fireStationService;
    private static MedicalRecordServiceImpl medicalRecordService;
    private final AllergiesService allergiesService;

    public LoadJsonFile(MedicalRecordServiceImpl medicalRecordService, PersonService personService, FireStationServiceImp fireStationService, AllergiesService allergiesService) {
        LoadJsonFile.personService = personService;
        LoadJsonFile.fireStationService = fireStationService;
        LoadJsonFile.medicalRecordService = medicalRecordService;
        this.allergiesService = allergiesService;
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
            List<Allergies> allergies = new ArrayList<>();
            medicalrecordsAny.forEach(a -> allergies.add(new Allergies().nameAllergies(a.get("allergies").toString())));
            allergiesService.saveAll(allergies);

                medicalrecordsAny.forEach(a -> medicalRecords.add(new MedicalRecord().firstname(a.get("firstName").toString()).lastname(a.get("lastName").toString()).birthdate(a.get("birthdate").toString()).medications(a.get("medications").toString()).build()));
                medicalRecordService.saveMedicalRecord(medicalRecords);
            }

        }




