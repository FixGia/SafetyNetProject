package com.project.apisafetynet.ControllerTest;

import com.project.apisafetynet.Controller.MedicalRecordController;
import com.project.apisafetynet.Controller.SpecificEndPointsController;
import com.project.apisafetynet.Service.FireStationService;
import com.project.apisafetynet.Service.LoadJsonFile;
import com.project.apisafetynet.Service.MedicalRecordService;
import com.project.apisafetynet.Service.PersonService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = SpecificEndPointsController.class)
public class SpecificEndPointsControllerTest {

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
    public void testGetCommunityEmail() throws Exception{
        mockMvc.perform(get("/communityEmail").param("city", "Culver")).andExpect(status().isOk());
    }
    @Test
    public void testGetPersonInfoByFireStation() throws  Exception{
        mockMvc.perform(get("/fireStation").param("station", "1")).andExpect(status().isOk());
    }
    @Test
    public void testGetPhoneNumber()throws Exception{
        mockMvc.perform(get("/phoneNumber").param("station", "1")).andExpect(status().isOk());
    }
    @Test
    public void testGetListPersonDeservedByStation() throws Exception{
        mockMvc.perform(get("/fire").param("address","1509 Culver St")).andExpect(status().isOk());
    }
    @Test
    public void testGetPersonInformationList() throws Exception{
        mockMvc.perform(get("/personInfo").param("firstName", "John").param("lastName", "Boyd")).andExpect(status().isOk());
    }
    @Test
    public void testGetChildAlter() throws Exception{
        mockMvc.perform(get("/childAlert").param("address", "1509 Culver St")).andExpect(status().isOk());
    }
    @Test
    public void testGetFloodByStations() throws Exception{
        mockMvc.perform(get("/flood/stations").param("stations", "1,2,3,4")).andExpect(status().isOk());
    }
}
