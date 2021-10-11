package com.project.apisafetynet.ControllerTest;
import com.project.apisafetynet.Controller.FireStationController;
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

@WebMvcTest(controllers = FireStationController.class)
public class FireStationControllerTest {

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
    public void testGetFireStations() throws Exception {
        mockMvc.perform(get("/firestation/firestations")).andExpect(status().isOk());
    }
    @Test
    public void testDeleteFireStation() throws Exception {
        mockMvc.perform(delete("/firestation").param ("Id", "1")).andExpect(status().isOk());
    }
    @Test
    public void testGetFireStation() throws Exception {
        mockMvc.perform(get("/firestation").param ("Id", "1")).andExpect(status().isOk());
    }
    @Test
    public void testUpdateFireStation() throws Exception{
        mockMvc.perform(put("/firestation").param ("Id", "1")).andExpect(status().isOk());
    }
    @Test
    public void testCreateFireStation() throws Exception{
        mockMvc.perform(post("/firestation").param ("Id", "1")).andExpect(status().isOk());
    }
    @Test
    public void testGetPersonInfoByFireStation() throws  Exception{
        mockMvc.perform(get("/firestation/fireStation").param("station", "1")).andExpect(status().isOk());
    }
    @Test
    public void testGetPhoneNumber()throws Exception{
        mockMvc.perform(get("/firestation/phoneNumber").param("station", "1")).andExpect(status().isOk());
    }
    @Test
    public void testGetListPersonDeservedByStation() throws Exception{
        mockMvc.perform(get("/firestation/fire").param("address","1509 Culver St")).andExpect(status().isOk());
    }
    @Test
    public void testGetFloodByStations() throws Exception{
        mockMvc.perform(get("/firestation/flood/stations").param("stations", "1,2,3,4")).andExpect(status().isOk());
    }
}