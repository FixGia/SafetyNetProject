package com.project.apisafetynet.ControllerTest;
import com.project.apisafetynet.Controller.PersonController;
import com.project.apisafetynet.Service.FireStationService;
import com.project.apisafetynet.Service.LoadJsonFile;
import com.project.apisafetynet.Service.MedicalRecordService;
import com.project.apisafetynet.Service.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = PersonController.class)
public class PersonControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    PersonService personService;
    @MockBean
    LoadJsonFile loadJsonFile;
    @MockBean
    FireStationService fireStationService;
    @MockBean
    MedicalRecordService medicalRecordService;


    @Test
    public void testGetPersons() throws Exception {
        mockMvc.perform(get("/person").param("firstName","John").param("lastName","Boyd")).andExpect(status().isOk());

    }
    @Test
    public void testDeletePerson() throws Exception{

        mockMvc.perform(delete("/person").param("firstName","John").param("lastName","Boyd")).andExpect(status().isOk());
    }
    @Test
    public void testUpdatePerson() throws Exception{
        mockMvc.perform(put("/person").param("firstName","John").param("lastName","Boyd")).andExpect(status().isOk());
    }
    @Test
    public void testCreatePerson() throws  Exception {
        mockMvc.perform(post("/person").param("firstName","John").param("lastName","Boyd")).andExpect(status().isOk());
    }
    @Test
    public void testGetPerson() throws Exception{
        mockMvc.perform(get("/person").param("firstName","John").param("lastName","Boyd")).andExpect(status().isOk());
    }
    @Test
    public void testGetCommunityEmail() throws Exception{
        mockMvc.perform(get("/person/communityEmail").param("city", "Culver")).andExpect(status().isOk());
    }

    @Test
    public void testGetPersonInformationList() throws Exception{
        mockMvc.perform(get("/person/personInfo").param("firstName", "John").param("lastName", "Boyd")).andExpect(status().isOk());
    }
    @Test
    public void testGetChildAlter() throws Exception{
        mockMvc.perform(get("/person/childAlert").param("address", "1509 Culver St")).andExpect(status().isOk());
    }

}

