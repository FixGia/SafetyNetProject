package com.project.apisafetynet.ControllerTest;
import com.project.apisafetynet.Controller.PersonController;
import com.project.apisafetynet.Service.LoadJsonFile;
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


    @Test
    public void testGetPersons() throws Exception {
        mockMvc.perform(get("/persons")).andExpect(status().isOk());

    }
    @Test
    public void testDeletePerson() throws Exception{

        mockMvc.perform(delete("/persons/Id","{Id}")).andExpect(status().isOk());
    }
    @Test
    public void testUpdatePerson() throws Exception{
        mockMvc.perform(put("/persons/Id")).andExpect(status().isOk());
    }
    @Test
    public void testCreatePerson() throws  Exception {
        mockMvc.perform(post("/persons/Id")).andExpect(status().isOk());
    }
    @Test
    public void testGetPerson() throws Exception{
        mockMvc.perform(get("/persons/Id")).andExpect(status().isOk());
    }
    }

