package com.project.apisafetynet.IntegrationTest;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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
        String bodyContent = "{\"firstName\":\"firstnameTest\",\"lastName\":\"lastnameTest\",\"birthdate\":\"birthDateTest1\",\"medications\":[{\"nameMedication\":\"aznol:350mg\"}],\"allergies\":[{\"nameAllergies\":\"shellfish\"}]}";
        mockMvc.perform(post("/medicalRecord") .contentType(MediaType.APPLICATION_JSON)
                .content(bodyContent)).andExpect(status().isCreated());
        mockMvc.perform(get("/medicalRecord").param("firstName", "firstnameTest").param("lastName", "lastnameTest")).andExpect(status().isOk());
    }
    @Test
    void updateMedicalRecordButForgetJsonBodyTest() throws Exception{
        mockMvc.perform(put("/medicalRecord").param("firstName", "Reginold").param("lastName", "Walker")).andExpect(status().isBadRequest());
    }
    @Test
    void updateMedicalRecordAndEverythingIsOk() throws Exception {
        String bodyContent = "{\"birthdate\":\"birthDateTest\",\"medications\":[{\"nameMedication\":\"aznol:350mg\"}],\"allergies\":[{\"nameAllergies\":\"shellfish\"}]}";
        mockMvc.perform(put("/medicalRecord").param("firstName", "Reginold").param("lastName", "Walker")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bodyContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("Reginold")))
                .andExpect(jsonPath("$.lastName", is("Walker")))
                .andExpect(jsonPath("$.birthdate", is("birthDateTest")))
                .andExpect(jsonPath("$.medications[0].nameMedication", is("aznol:350mg")))
                .andExpect(jsonPath("$.allergies[0].nameAllergies", is("shellfish")));
    }

}

