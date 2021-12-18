package com.project.apisafetynet.ControllerTest;
import com.project.apisafetynet.Controller.PersonController;
import com.project.apisafetynet.Service.FireStationService;
import com.project.apisafetynet.Service.LoadJsonFile;
import com.project.apisafetynet.Service.MedicalRecordService;
import com.project.apisafetynet.Service.PersonService;
import com.project.apisafetynet.model.DTO.PersonRequest;
import com.project.apisafetynet.model.DTO.ChildrenAndFamilyMembers;
import com.project.apisafetynet.model.DTO.PersonInformation;

import com.project.apisafetynet.model.ModelRepository.Person;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PersonController.class)
@AutoConfigureMockMvc
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
    public void testGetPerson() throws Exception{
        Person personTest = new Person();
        personTest.setFirstName("firstNameTest");
        personTest.setLastName("lastNameTest");
        personTest.setAddress("AddressTest");
        personTest.setCity("cityTest");
        personTest.setZip("00000");
        personTest.setPhone("000000000");
        personTest.setEmail("emailTest");
        String bodyContent = "{\n" +
                "\"firstName\":\"firstNameTest\",\n" +
                "\"lastName\":\"lastNameTest\",\n" +
                "\"address\":\"AddressTest\",\n" +
                "\"city\":\"cityTest\",\n" +
                "\"zip\":\"00023\",\n" +
                "\"phone\":\"000000023\",\n" +
                "\"email\":\"emailTest\"\n" +
                "}";
        when(personService.getPerson((any(String.class)),any(String.class))).thenReturn(Optional.of(personTest));
        mockMvc.perform(get("/person").param("firstName", "firstNameTest")
                .param("lastName", "lastNameTest")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bodyContent)).andExpect(status().isOk());
    }

    @Test
    public void testCreatePerson() throws  Exception {
        mockMvc.perform(post("/person").contentType(MediaType.APPLICATION_JSON).content("{}")).andExpect(status().isCreated());
    }

    @Test
    public void testUpdatePerson() throws Exception{
        Person personTest = new Person();
        personTest.setFirstName("firstNameTest");
        personTest.setLastName("lastNameTest");
        personTest.setAddress("AddressTest");
        personTest.setCity("cityTest");
        personTest.setZip("00000");
        personTest.setPhone("000000000");
        personTest.setEmail("emailTest");
        String bodyContent = "{\n" +
                "\"firstName\":\"firstNameTest\",\n" +
                "\"lastName\":\"lastNameTest\",\n" +
                "\"address\":\"AddressTest\",\n" +
                "\"city\":\"cityTest\",\n" +
                "\"zip\":\"00023\",\n" +
                "\"phone\":\"000000023\",\n" +
                "\"email\":\"emailTest\"\n" +
                "}";
        when(personService.updatePerson(any(String.class),any(String.class),any(PersonRequest.class))).thenReturn(personTest);
        mockMvc.perform(put("/person").param("firstName", "firstNameTest")
                        .param("lastName", "lastNameTest")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bodyContent))
                .andExpect(status().isOk());
    }


    @Test
    void UpdatePersonButPersonNotExistTest() throws Exception {
        mockMvc.perform(put("/person")
                .param("firstName", "David").param("lastName", "Hassel").contentType(MediaType.APPLICATION_JSON)
                .content("{}")).andExpect(status().isNotFound());
    }

    @Test
    public void testCreatePersonButAlreadyExist() throws  Exception {
        Person personTest = new Person();

        when(personService.getPerson((any(String.class)),any(String.class))).thenReturn(Optional.of(personTest));
        mockMvc.perform(post("/person").param("firstName","firstNameTest").param("lastName","lastNameTest")).andExpect(status().isBadRequest());
    }

    @Test
    public void testGetPersonsButHeDoesntExist() throws Exception {
        mockMvc.perform(get("/person").param("firstName","PersonFirstNameNotExist").param("lastName","PersonLastNameNotExist")).andExpect(status().isNotFound());
    }

    @Test
    public void testGetCommunityEmail() throws Exception{
        Person personTest = new Person();
        ArrayList<String> listOfCity = new ArrayList<>();
        listOfCity.add(personTest.getCity());

        when(personService.savePerson(any(Person.class))).thenReturn(Optional.of(personTest));
        when(personService.getEmailPersonByCity(any(String.class))).thenReturn(listOfCity);

        mockMvc.perform(get("/communityEmail").param("city", "cityTest")).andExpect(status().isOk());
    }


    @Test
    public void testGetCommunityEmailButCityDoesntExist() throws Exception {
        mockMvc.perform(get("/communityEmail").param("city", "Culver")).andExpect(status().isNotFound());
    }
    @Test
    public void testGetPersonInformationList() throws Exception{

        Person personTest = new Person();

        PersonInformation personInformation = new PersonInformation();
        personInformation.setAddress(personTest.getAddress());
        personInformation.setFirstName(personTest.getFirstName());
        personInformation.setLastName(personTest.getLastName());

        ArrayList<PersonInformation> personInfoList = new ArrayList<>();
        personInfoList.add(personInformation);

        when(personService.getPersonInformation(any(String.class), any(String.class))).thenReturn(personInfoList);

        mockMvc.perform(get("/personInfo").param("firstName", "firstNameTest").param("lastName", "lastNameTest")).andExpect(status().isOk());
    }

    @Test
    public void testGetPersonInformationListButPersonDoesntExist() throws Exception{
        mockMvc.perform(get("/personInfo").param("firstName", "John").param("lastName", "Boyd")).andExpect(status().isNotFound());
    }

    @Test
    public void testGetChildAlert() throws Exception{

        ChildrenAndFamilyMembers childrenAndFamilyMembers = new ChildrenAndFamilyMembers();

        when(personService.getListOfChildrenAndFamilyMembersByAddress(any(String.class))).thenReturn(Optional.of(childrenAndFamilyMembers));

        mockMvc.perform(get("/childAlert").param("address", "addressTest")).andExpect(status().isOk());
    }

    @Test

    public void testGetChildAlertButAddressDoesntExist() throws Exception{
        mockMvc.perform(get("/childAlert").param("address", "1509 Culver St")).andExpect(status().isNotFound());
    }

    @Test

    public void testDeletePerson() throws Exception{
        Person personTest = new Person();

        when(personService.getPerson((any(String.class)),any(String.class))).thenReturn(Optional.of(personTest));

        mockMvc.perform(delete("/person").param("firstName","firstNameTest").param("lastName","lastNameTest")).andExpect(status().isOk());
    }
    @Test

    public void testDeletePersonButHeDoesntExist() throws Exception {
        mockMvc.perform(delete("/person").param("firstName","John").param("lastName","Boyd")).andExpect(status().isNotFound());
    }
}

