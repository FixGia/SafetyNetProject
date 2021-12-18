package com.project.apisafetynet.IntegrationTest;


import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.*;

import static org.hamcrest.CoreMatchers.is;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.MultiValueMap;


@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PersonControllerIT {

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    void initializeDataBase() {
    }

    @Order(1)
    @Test
    void getChildAndFamilyMembersTest() throws Exception {
        mockMvc.perform(get("/childAlert")
                        .param("address", "1509 Culver St"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("childrenArrayList[0].firstName", is("Tenley")))
                .andExpect(jsonPath("childrenArrayList[0].lastName", is("Boyd")))
                .andExpect(jsonPath("childrenArrayList[0].age", is(9)));

    }

    @Order(2)
    @Test
    void getPersonInfoTest() throws Exception {
        mockMvc.perform(get("/personInfo").param("firstName", "John").param("lastName", "Boyd"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName", is("John")))
                .andExpect(jsonPath("$[0].lastName", is("Boyd")))
                .andExpect(jsonPath("$[0].address", is("1509 Culver St")))
                .andExpect(jsonPath("$[0].age", is(37)))
                .andExpect(jsonPath("$[0].medications[0].nameMedication", is("aznol:350mg")))
                .andExpect(jsonPath("$[0].allergies[0].nameAllergies", is("nillacilan")));
    }

    @Order(3)
    @Test
    void UpdatePersonTest() throws Exception {
        String bodyContent ="{ \"address\": \"string\"," +
                "\"city\": \"string\"," +
                " \"email\": \"string\"," +
                " \"firstName\": \"string\"," +
                " \"lastName\": \"string\"," +
                " \"phone\": \"string\"," +
                " \"zip\": \"string\"}";
        mockMvc.perform(put("/person").param("firstName", "John").param("lastName", "Boyd")
                        .contentType(MediaType.APPLICATION_JSON).content(bodyContent))
                .andExpect(status().isOk());
        mockMvc.perform(get("/person")
                        .param("firstName", "string")
                        .param("lastName", "string"))
                .andExpect(jsonPath("$.firstName", is("string")))
                .andExpect(jsonPath("$.lastName", is("string")))
                .andExpect(jsonPath("$.address", is("string")))
                .andExpect(jsonPath("$.email", is("string")))
                .andExpect(jsonPath("$.zip", is("string")))
                .andExpect(jsonPath("$.phone", is("string")))
                .andExpect(status().isOk());
    }

    @Test
    @Order(6)
    void UpdatePersonButDoesntExist() throws Exception {
        String bodyContent ="{ \"address\": \"string\"," +
                "\"city\": \"string\"," +
                " \"email\": \"string\"," +
                " \"firstName\": \"string\"," +
                " \"lastName\": \"string\"," +
                " \"phone\": \"string\"," +
                " \"zip\": \"string\"}";
        mockMvc.perform(put("/person")
                        .param("firstName", "firstNameTest2").param("lastName", "lastNameTest2")
                        .contentType(MediaType.APPLICATION_JSON).content(bodyContent))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(4)
    void CreatePersonTest() throws Exception {
        String bodyContent ="{ \"address\": \"12 rue du test\"," +
                "\"city\": \"Victory\"," +
                " \"email\": \"Test@gmail.com\"," +
                " \"firstName\": \"Jean\"," +
                " \"lastName\": \"Test\"," +
                " \"phone\": \"050505050\"," +
                " \"zip\": \"0000\"}";
        mockMvc.perform(post("/person")
                        .contentType(MediaType.APPLICATION_JSON).content(bodyContent))
                .andExpect(status().isCreated());
        mockMvc.perform(get("/person").param("firstName", "Jean").param("lastName", "Test")).andExpect(status().isOk());
    }

    @Test
    @Order(5)
    void DeletePersonTest() throws Exception {
        mockMvc.perform(delete("/person").param("firstName","Jacob").param("lastName", "Boyd")).andExpect(status().isOk());
        mockMvc.perform(get("/person").param("firstName","Jacob").param("lastName", "Boyd")).andExpect(status().isNotFound());
    }
}



