package com.project.apisafetynet.IntegrationTest;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MedicalRecordControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Test
    void getMedicalRecordTest() throws Exception {
        mockMvc.perform(get("/medicalRecord").param("firstName", "John").param("lastName", "Boyd"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("John")))
                .andExpect(jsonPath("$.lastName", is("Boyd")))
                .andExpect(jsonPath("$.birthdate", is("03/06/1984")));
    }

    @Test
    void getMedicalRecordTestButItDoesntExist() throws Exception {
        mockMvc.perform(get("/medicalRecord").param("firstName", "Jon").param("lastName", "Boyd"))
                .andExpect(status().isNotFound());

    }

    @Test
    void deleteMedicalRecordTest() throws Exception {
        mockMvc.perform(delete("/medicalRecord").param("firstName", "John").param("lastName", "Boyd")).andExpect(status().isOk());
        mockMvc.perform(get("/medicalRecord").param("firstName", "John").param("lastName", "Boyd")).andExpect(status().isNotFound());
    }

    @Test
    void createMedicalRecordTest() throws Exception {
        mockMvc.perform(post("/medicalRecord").param("firstName", "firstNameTest").param("lastName", "lastNameTest")).andExpect(status().isCreated());
        mockMvc.perform(get("/medicalRecord").param("firstName", "firstNameTest").param("lastName", "lastNameTest")).andExpect(status().isOk());
    }
}

