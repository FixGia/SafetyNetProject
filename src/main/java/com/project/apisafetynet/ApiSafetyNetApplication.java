package com.project.apisafetynet;

import com.project.apisafetynet.Service.FireStationService;
import com.project.apisafetynet.Service.LoadJsonFile;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiSafetyNetApplication implements CommandLineRunner {

    final LoadJsonFile loadJsonFile;


    public ApiSafetyNetApplication(LoadJsonFile loadJsonFile) {
        this.loadJsonFile = loadJsonFile;

    }

    public static void main(String[] args) {
        SpringApplication.run(ApiSafetyNetApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        loadJsonFile.readPersons();
        loadJsonFile.readFireStation();
        loadJsonFile.readMedicalRecords();




    }
}
