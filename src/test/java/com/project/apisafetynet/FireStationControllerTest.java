package com.project.apisafetynet;
import com.project.apisafetynet.Controller.FireStationController;
import com.project.apisafetynet.Service.FireStationService;
import com.project.apisafetynet.Service.LoadJsonFile;
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
    LoadJsonFile loadJsonFile;
    @MockBean
    FireStationService fireStationService;


    @Test
    public void testGetFireStations() throws Exception {
        mockMvc.perform(get("/firestations")).andExpect(status().isOk());
    }
    @Test
    public void testDeleteFireStation() throws Exception {
        mockMvc.perform(delete("/firestations/1")).andExpect(status().isOk());
    }
    @Test
    public void testGetFireStation() throws Exception {
        mockMvc.perform(get("/firestations/1")).andExpect(status().isOk());
    }
    @Test
    public void testUpdateFireStation() throws Exception{
        mockMvc.perform(put("/firestations/1")).andExpect(status().isOk());
    }
    @Test
    public void testCreateFireStation() throws Exception{
        mockMvc.perform(post("/firestations")).andExpect(status().isOk());

    }
}