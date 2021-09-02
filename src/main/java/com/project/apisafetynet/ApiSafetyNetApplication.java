package com.project.apisafetynet;

import com.project.apisafetynet.Dao.LoadJsonFile;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiSafetyNetApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ApiSafetyNetApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        LoadJsonFile.readPersons();
        LoadJsonFile.readFireStation();

    }
}
