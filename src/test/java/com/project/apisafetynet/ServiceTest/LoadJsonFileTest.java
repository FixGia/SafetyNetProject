package com.project.apisafetynet.ServiceTest;

import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;
import com.project.apisafetynet.Repository.FireStationRepository;
import com.project.apisafetynet.Repository.MedicalRecordRepository;
import com.project.apisafetynet.Repository.PersonRepository;
import com.project.apisafetynet.Service.FireStationServiceImp;
import com.project.apisafetynet.Service.MedicalRecordServiceImpl;
import com.project.apisafetynet.Service.PersonServiceImpl;
import com.project.apisafetynet.model.ModelRepository.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class LoadJsonFileTest {

    private static String filePath = "src/main/resources/data.json";
    private static byte[] bytesFile;

    @Mock
    FireStationRepository fireStationRepository;
    @Mock
    MedicalRecordRepository medicalRecordRepository;
    @Mock
    PersonRepository personRepository;
    @InjectMocks
    MedicalRecordServiceImpl medicalRecordService;
    @InjectMocks
    PersonServiceImpl personService;
    @InjectMocks
    FireStationServiceImp fireStationService;


    @BeforeEach
    private void setUpBeforeEachTests() {

        String filePath = "src/main/resources/dataTest.json";
    }

    @Test
    public void readPersonFromJsonFileTest() throws IOException {

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

    }
}