package com.project.apisafetynet.ControllerTest;
import com.project.apisafetynet.Controller.FireStationController;
import com.project.apisafetynet.Service.FireStationService;
import com.project.apisafetynet.Util.LoadJsonFile;
import com.project.apisafetynet.Service.MedicalRecordService;
import com.project.apisafetynet.Service.PersonService;
import com.project.apisafetynet.model.DTO.*;
import com.project.apisafetynet.model.ModelRepository.FireStation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
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
        FireStation fireStation = new FireStation();

        when(fireStationService.getFireStation((any(Long.class)))).thenReturn(Optional.of(fireStation));
        mockMvc.perform(get("/firestations")).andExpect(status().isOk());
    }
    @Test
    public void testDeleteFireStation() throws Exception {
        FireStation fireStation = new FireStation();
        when(fireStationService.getFireStation(any(Long.class))).thenReturn(Optional.of(fireStation));
        mockMvc.perform(delete("/firestation").param ("Id", "1")).andExpect(status().isOk());
    }
    @Test
    public void testDeleteFireStationButItDoesntExist() throws Exception {
        mockMvc.perform(delete("/firestation").param ("Id", "2")).andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateFireStation() throws Exception{
        FireStation fireStation = new FireStation();
        when(fireStationService.updateFireStation(any(Long.class), any(FirestationRequest.class))).thenReturn(Optional.of(fireStation));
        mockMvc.perform(put("/firestation").param ("Id", "1").contentType(MediaType.APPLICATION_JSON).content("{}")).andExpect(status().isOk());
    }
    @Test
    public void testUpdateFireStationButItDoesntExist() throws Exception{
        mockMvc.perform(put("/firestation").param ("Id", "2").contentType(MediaType.APPLICATION_JSON).content("{}")).andExpect(status().isNotFound());
    }
    @Test
    public void testCreateFireStation() throws Exception{
        mockMvc.perform(post("/firestation").contentType(MediaType.APPLICATION_JSON).content("{}")).andExpect(status().isCreated());
    }
    @Test
    public void testCreateFireStationButItAlreadyExist() throws Exception{
        FireStation fireStation = new FireStation();
        when(fireStationService.getFireStation(any(Long.class))).thenReturn(Optional.of(fireStation));
        mockMvc.perform(post("/firestation").param ("Id", "1")).andExpect(status().isBadRequest());
    }
    @Test
    public void testGetPersonInfoByFireStation() throws  Exception{
        InfoByZone infoByZone = new InfoByZone();
        when(fireStationService.getListPersonInformationByFireStation(any(String.class))).thenReturn(Optional.of(infoByZone));
        mockMvc.perform(get("/firestation").param("station", "1")).andExpect(status().isOk());
    }
    @Test
    public void testGetPersonInfoByFireStationButItDoesntExist() throws  Exception{
        mockMvc.perform(get("/firestation").param("station", "2")).andExpect(status().isNotFound());
    }
    @Test
    public void testGetPhoneNumber()throws Exception{
        PhoneAlert phoneAlert = new PhoneAlert();
        phoneAlert.setPhone("0685156");
        ArrayList<PhoneAlert> phoneAlerts = new ArrayList<>();
        phoneAlerts.add(phoneAlert);

        when(fireStationService.getPhoneNumberByStation(any(String.class))).thenReturn(phoneAlerts);
        mockMvc.perform(get("/phoneNumber").param("station", "1")).andExpect(status().isOk());
    }
    @Test
    public void testGetPhoneNumberButStationDoesntExist()throws Exception{
        mockMvc.perform(get("/phoneNumber").param("station", "1")).andExpect(status().isNotFound());
    }
    @Test
    public void testGetListPersonDeservedByStation() throws Exception{
        PersonsAndFireStationWhoDeservedThem personsAndFireStationWhoDeservedThem = new PersonsAndFireStationWhoDeservedThem();
        ArrayList<PersonsAndFireStationWhoDeservedThem> personsAndFireStationWhoDeservedThems = new ArrayList<>();
        personsAndFireStationWhoDeservedThems.add(personsAndFireStationWhoDeservedThem);
        when(fireStationService.getPersonListAndStationNumber(any(String.class))).thenReturn(personsAndFireStationWhoDeservedThems);
        mockMvc.perform(get("/fire").param("address","1509 Culver St")).andExpect(status().isOk());
    }
    @Test
    public void testGetListPersonDeservedByStationButAddressDoesntExist() throws Exception{
        mockMvc.perform(get("/fire").param("address","1509 Culver St")).andExpect(status().isNotFound());
    }
    @Test
    public void testGetFloodByStations() throws Exception{
        ArrayList<Flood> floodArrayList= new ArrayList<>();
        List<FloodMembers> floodMembersList = new ArrayList<>();
        Flood flood = new Flood("address", floodMembersList);
        floodArrayList.add(flood);

        when(fireStationService.getListOfFloodsByStations(any(ArrayList.class))).thenReturn(floodArrayList);
        mockMvc.perform(get("/flood/stations").param("stations", "1,2,3,4")).andExpect(status().isOk());
    }
    @Test
    public void testGetFloodByStationsButListStationsDoesntRight() throws Exception{
        mockMvc.perform(get("/flood/stations").param("stations", "1,2,3,4")).andExpect(status().isNotFound());
    }
}