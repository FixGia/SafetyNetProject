package com.project.apisafetynet.ControllerTest;

import com.project.apisafetynet.Controller.MedicalRecordController;
import com.project.apisafetynet.Service.LoadJsonFile;
import com.project.apisafetynet.Service.MedicalRecordService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MedicalRecordController.class)
public class MedicalRecordControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    LoadJsonFile loadJsonFile;
    @MockBean
    MedicalRecordService medicalRecordService;

    @Test
    public void testGetMedicalRecord() throws Exception{
        mockMvc.perform(get("/medicalRecord").param("firstName","John").param("lastName","Boyd")).andExpect(status().isOk());
    }
    @Test
    public void testCreateMedicalRecord() throws Exception{
        mockMvc.perform(post("/medicalRecord").param("firstName","John").param("lastName","Boyd")).andExpect(status().isOk());
    }
    @Test
    public void testDeleteMedicalRecord() throws Exception{
        mockMvc.perform(delete("/medicalRecord").param("firstName","John").param("lastName","Boyd")).andExpect(status().isOk());
    }
    @Test
    public void testUpdateMedicalRecord() throws Exception{
        mockMvc.perform(put("/medicalRecord").param("firstName","John").param("lastName","Boyd")).andExpect(status().isOk());
    }
    @Test
    public void testGetAllMedicalRecords() throws Exception{
        mockMvc.perform(get("/medicalRecord/medicalRecords")).andExpect(status().isOk());
    }
}

