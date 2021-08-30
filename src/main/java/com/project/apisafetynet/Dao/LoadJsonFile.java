package com.project.apisafetynet.Dao;
import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;
import com.project.apisafetynet.PersonRepository;
import com.project.apisafetynet.Service.PersonService;
import com.project.apisafetynet.model.Persons;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;


@Component
public class LoadJsonFile {


    public static void readJsonFile() throws IOException {

        byte[] bytesFile;
        final String filePath = "src/main/resources/data.json";
        bytesFile = Files.readAllBytes(new File(filePath).toPath());
        JsonIterator iter = JsonIterator.parse(bytesFile);
        Any any = iter.readAny();
        Any personAny = any.get("persons");
        List<Persons> persons = new ArrayList<>();
        personAny.forEach(a -> persons.add(new Persons().firstName(a.get("firstName").toString())
                .address(a.get("address").toString())
                .city(a.get("city").toString())
                .lastName(a.get("lastName").toString())
                .phone(a.get("phone").toString())
                .zip(a.get("zip").toString())
                .email(a.get("email").toString())
                .build()));
        PersonService personService = new PersonService();


        persons.forEach(p -> System.out.println(p.firstName.concat(p.lastName).concat(p.address).concat(p.city).concat(p.phone).concat(p.zip)));







    }
}

