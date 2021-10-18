package com.project.apisafetynet.ControllerTest;

import com.project.apisafetynet.Controller.MedicalRecordController;
import com.project.apisafetynet.Service.LoadJsonFile;
import com.project.apisafetynet.Service.MedicalRecordService;

import com.project.apisafetynet.model.ModelRepository.Allergies;
import com.project.apisafetynet.model.ModelRepository.MedicalRecord;
import com.project.apisafetynet.model.ModelRepository.Medications;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
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
        MedicalRecord medicalRecord = new MedicalRecord();

        when(medicalRecordService.getMedicalRecord(any(String.class), any(String.class))).thenReturn(Optional.of(medicalRecord));
        mockMvc.perform(get("/medicalRecord").param("firstName","firstNameTest").param("lastName","lastNameTest")).andExpect(status().isOk());
    }
    @Test
    public void testGetMedicalRecordButMedicalRecordDoesntExist() throws Exception{
        mockMvc.perform(get("/medicalRecord").param("firstName","firstNameTest").param("lastName","lastNameTest")).andExpect(status().isNotFound());
    }
    @Test
    public void testCreateMedicalRecord() throws Exception{
        MedicalRecord medicalRecord =new MedicalRecord();
        when(medicalRecordService.saveMedicalRecord(any(MedicalRecord.class))).thenReturn(Optional.of(medicalRecord));
        mockMvc.perform(post("/medicalRecord").param("firstName","firstNameTest").param("lastName","lastNameTest")).andExpect(status().isCreated());
    }
    @Test
    public void testCreateMedicalRecordButMedicalRecordAlreadyExist() throws Exception{
        MedicalRecord medicalRecord = new MedicalRecord();
        when(medicalRecordService.getMedicalRecord(any(String.class), any(String.class))).thenReturn(Optional.of(medicalRecord));
        mockMvc.perform(post("/medicalRecord").param("firstName","firstNameTest").param("lastName","lastNameTest")).andExpect(status().isBadRequest());
    }
    @Test
    public void testDeleteMedicalRecord() throws Exception{
        MedicalRecord medicalRecord = new MedicalRecord();

        when(medicalRecordService.getMedicalRecord(any(String.class), any(String.class))).thenReturn(Optional.of(medicalRecord));

        mockMvc.perform(delete("/medicalRecord").param("firstName","firstNameTest").param("lastName","lastNameTest")).andExpect(status().isOk());
    }
    @Test
    public void testDeleteMedicalRecordButItDoesntExist() throws Exception{
        mockMvc.perform(delete("/medicalRecord").param("firstName","firstNameTest").param("lastName","lastNameTest")).andExpect(status().isNotFound());
    }
    @Test
    public void testUpdateMedicalRecord() throws Exception{

        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setFirstName("firstNameTest");
        medicalRecord.setLastName("lastNameTest");
        medicalRecord.setBirthdate("12/12/2012");
        medicalRecord.setMedications(List.of(new Medications()));
        medicalRecord.setAllergies(List.of(new Allergies()));

        when(medicalRecordService.updateMedicalRecord(any(MedicalRecord.class),any(String.class), any(String.class))).thenReturn(Optional.of(medicalRecord));
        mockMvc.perform(put("/medicalRecord").param("firstName","firstNameTest").param("lastName","lastNameTest")).andExpect(status().isOk());

    }
    @Test
    public void testUpdateMedicalRecordButItDoesntExist() throws Exception{
        mockMvc.perform(put("/medicalRecord").param("firstName","firstNameTest").param("lastName","lastNameTest")).andExpect(status().isNotFound());

    }

}

