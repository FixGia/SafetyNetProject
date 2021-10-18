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

import org.springframework.test.web.servlet.MockMvc;


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
        mockMvc.perform(put("/person")
                        .param("firstName", "John").param("lastName", "Boyd").param("address", "addressModify").param("email", "emailModify"))
                .andExpect(status().isOk());
        mockMvc.perform(get("/person")
                        .param("firstName", "John")
                        .param("lastName", "Boyd"))
                .andExpect(jsonPath("$.firstName", is("John")))
                .andExpect(jsonPath("$.lastName", is("Boyd")))
                .andExpect(jsonPath("$.address", is("addressModify")))
                .andExpect(jsonPath("$.email", is("emailModify")))
                .andExpect(status().isOk());
    }

    @Test
    @Order(4)
    void CreatePersonTest() throws Exception {

        mockMvc.perform(post("/person")
                .param("firstName", "firstNameTest")
                .param("lastName", "lastNameTest"))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/person").param("firstName", "firstNameTest").param("lastName", "lastNameTest")).andExpect(status().isOk());
    }

    @Test
    @Order(5)
    void DeletePersonTest() throws Exception {
        mockMvc.perform(delete("/person").param("firstName","John").param("lastName", "Boyd")).andExpect(status().isOk());
        mockMvc.perform(get("/person").param("firstName","John").param("lastName", "Boyd")).andExpect(status().isNotFound());
    }
}



