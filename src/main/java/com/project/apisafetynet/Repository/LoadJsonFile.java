package com.project.apisafetynet.Repository;
import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;
import com.project.apisafetynet.Service.FireStationServiceImp;
import com.project.apisafetynet.Service.MedicalRecordServiceImpl;
import com.project.apisafetynet.Service.PersonService;
import com.project.apisafetynet.model.FireStation;
import com.project.apisafetynet.model.MedicalRecord;
import com.project.apisafetynet.model.Person;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Data
@Component
public class LoadJsonFile {

    private static String filePath = "src/main/resources/data.json";
    private static byte[] bytesFile;
    private static PersonService personService;
    private static FireStationServiceImp fireStationService;
    private static MedicalRecordServiceImpl medicalRecordService;

    public LoadJsonFile(MedicalRecordServiceImpl medicalRecordService,PersonService personService,FireStationServiceImp fireStationService,FireStationRepository fireStationRepository) {
        this.personService = personService;
        this.fireStationService = fireStationService;
        this.medicalRecordService = medicalRecordService;
    }

    public static void readPersons() throws IOException {

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


    public static void readFireStation() throws IOException {

        bytesFile = Files.readAllBytes(new File(filePath).toPath());
        JsonIterator iter = JsonIterator.parse(bytesFile);
        Any any = iter.readAny();
        Any fireStationAny = any.get("firestations");
        List<FireStation> firestations = new ArrayList<>();
        fireStationAny.forEach(a -> firestations.add(new FireStation().address(a.get("address").toString()).station(a.get("station").toString())
                .build()));
        firestations.forEach(f -> System.out.println(f.address.concat("  "+f.station)));
        fireStationService.saveFireStations(firestations);


    }
    public static void readMedicalRecords() throws IOException {
        bytesFile = Files.readAllBytes(new File(filePath).toPath());
        JsonIterator iter = JsonIterator.parse(bytesFile);
        Any any = iter.readAny();
        Any medicalrecordsAny = any.get("medicalrecords");
        List<MedicalRecord> medicalRecords = new ArrayList<>();
        medicalrecordsAny.forEach(a -> medicalRecords.add(new MedicalRecord().firstname(a.get("firstName").toString()).lastname(a.get("lastName").toString()).birthdate(a.get("birthdate").toString()).medications(a.get("medications").toString()).allergies(a.get("allergies").toString()).build()));
        medicalRecords.forEach(m -> System.out.print(m.firstname.concat(m.lastname).concat(m.birthdate).concat(m.medications).concat(m.allergies)));
        medicalRecordService.saveMedicalRecord(medicalRecords);

    }

}

