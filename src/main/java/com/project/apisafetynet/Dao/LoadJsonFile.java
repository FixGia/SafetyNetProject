package com.project.apisafetynet.Dao;
import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;
import com.project.apisafetynet.Repository.PersonRepository;
import com.project.apisafetynet.Service.FireStationServiceImp;
import com.project.apisafetynet.Service.PersonServiceImpl;
import com.project.apisafetynet.model.Persons;
import com.project.apisafetynet.model.Firestation;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

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
    private static PersonServiceImpl personService;





    public static void readPersons() throws IOException {

        bytesFile = Files.readAllBytes(new File(filePath).toPath());
        JsonIterator iter = JsonIterator.parse(bytesFile);
        Any any = iter.readAny();
        Any personAny = any.get("Persons");
        List<Persons> persons = new ArrayList<>();
        personAny.forEach(a -> persons.add(new Persons().firstName(a.get("firstName").toString())
                .address(a.get("address").toString())
                .city(a.get("city").toString())
                .lastName(a.get("lastName").toString())
                .phone(a.get("phone").toString())
                .zip(a.get("zip").toString())
                .email(a.get("email").toString())
                .build()));

        persons.forEach(p -> System.out.println(p.firstName.concat(p.lastName).concat(p.address).concat(p.city).concat(p.phone).concat(p.zip)));



    }


    public static void readFireStation() throws IOException {

        bytesFile = Files.readAllBytes(new File(filePath).toPath());
        JsonIterator iter = JsonIterator.parse(bytesFile);
        Any any = iter.readAny();
        Any fireStationAny = any.get("firestations");
        List<Firestation> firestations = new ArrayList<>();
        fireStationAny.forEach(a -> firestations.add(new Firestation().address(a.get("address").toString()).station(a.get("station").toString())
                .build()));
        firestations.forEach(f -> System.out.println(f.address.concat("  "+f.station)));



    }
    public static void readMedicalRecords() {

    }

}

