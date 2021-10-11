package com.project.apisafetynet.Service;
import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;
import com.project.apisafetynet.model.ModelRepository.FireStation;
import com.project.apisafetynet.model.ModelRepository.MedicalRecord;
import com.project.apisafetynet.model.ModelRepository.Person;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
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

    private static String filePath = "src/main/resources/data.json";
    private static byte[] bytesFile;
    private static PersonService personService;
    private static FireStationServiceImp fireStationService;
    private static MedicalRecordServiceImpl medicalRecordService;

    public LoadJsonFile(MedicalRecordServiceImpl medicalRecordService, PersonService personService, FireStationServiceImp fireStationService) {
        LoadJsonFile.personService = personService;
        LoadJsonFile.fireStationService = fireStationService;
        LoadJsonFile.medicalRecordService = medicalRecordService;
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
        medicalrecordsAny.forEach(a -> medicalRecords.add(new MedicalRecord().firstname(a.get("firstName").toString()).lastname(a.get("lastName").toString()).birthdate(a.get("birthdate").toString()).medications(a.get("medications").toString()).allergies(a.get("allergies").toString()).build()));
        medicalRecords.forEach(m -> log.info(m.firstname.concat(m.lastname).concat(m.birthdate).concat(m.medications).concat(m.allergies)));
        medicalRecordService.saveMedicalRecord(medicalRecords);

    }

}

