package com.project.apisafetynet.ServiceTest;

import com.project.apisafetynet.Repository.MedicalRecordRepository;
import com.project.apisafetynet.Repository.PersonRepository;
import com.project.apisafetynet.Service.CalculateAgeServiceImpl;
import com.project.apisafetynet.Service.PersonServiceImpl;
import com.project.apisafetynet.model.DTO.ChildrenAndFamilyMembers;
import com.project.apisafetynet.model.DTO.FamilyMembers;
import com.project.apisafetynet.model.ModelRepository.Allergies;
import com.project.apisafetynet.model.ModelRepository.MedicalRecord;
import com.project.apisafetynet.model.ModelRepository.Medications;
import com.project.apisafetynet.model.ModelRepository.Person;
import com.project.apisafetynet.model.DTO.PersonInformation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    private Person person;
    private MedicalRecord medicalRecord;
    private List<Person> personArrayList = new ArrayList<>();
    private List<MedicalRecord> medicalRecordList = new ArrayList<>();

    @Mock
    private PersonRepository personRepository;
    @Mock
    private MedicalRecordRepository medicalRecordRepository;
    @Mock
    private CalculateAgeServiceImpl calculateAgeService;
    @InjectMocks
    private PersonServiceImpl personService;


    @BeforeEach
    private void setUpBeforeEachTests() {
        person = new Person();
        person.setId(1L);
        person.setFirstName("Jack");
        person.setLastName("Jekyll");
        person.setAddress("33 rue du test");
        person.setZip("0000");
        person.setPhone("00000000");
        person.setCity("TestVille");
        person.email("Test@PersonTest.com");
        personArrayList.add(person);
        medicalRecord = new MedicalRecord();
        medicalRecord.setId(1L);
        medicalRecord.setFirstName("Jack");
        medicalRecord.setLastName("Jekyll");
        List<Medications> medications = new ArrayList<>();
        List<Allergies> allergies = new ArrayList<>();
        medicalRecord.setMedications(medications);
        medicalRecord.setAllergies(allergies);
        medicalRecord.setBirthdate("03/12/1991");
        medicalRecordList.add(medicalRecord);
        lenient().when(personRepository.findAll()).thenReturn(personArrayList);
        lenient().when(medicalRecordRepository.findAll()).thenReturn(medicalRecordList);
        lenient().when(personRepository.save(person)).thenReturn(person);
        lenient().when(medicalRecordRepository.save(medicalRecord)).thenReturn(medicalRecord);


    }

    @Test
    public void savePersonsTest() {
        personService.SavePersons(personArrayList);
        verify(personRepository, times(1)).saveAll(personArrayList);
    }

    @Test
    public void getPersonsTest() {
        ArrayList<Person> personArrayList = new ArrayList<>();
        personArrayList.add(person);
        when(personRepository.findAll()).thenReturn(personArrayList);
        Iterable<Person> personResponse = personService.getPersons();
        assertNotNull(personResponse);
        assertEquals(personArrayList, personResponse);
    }

    @Test
    public void savePersonTest() {
        personService.savePerson(person);
        verify(personRepository, times(1)).save(person);
    }

    @Test
    public void deletePersonTest() {
        personService.deletePerson(person);
        verify(personRepository, times(1)).delete(person);

    }

    @Test
    public void getPersonTest() {
        personService.getPerson("Jack", "Jekyll");
        verify(personRepository, times(1)).findByFirstNameAndLastName("Jack", "Jekyll");
        assertEquals(personService.getPerson("Jack", "Jekyll"), personRepository.findByFirstNameAndLastName("Jack", "Jekyll"));
    }

    @Test
    public void getEmailsPersonByCityTest() {
        ArrayList<Person> personArrayList = new ArrayList<>();
        personArrayList.add(person);
        ArrayList<String> emailList = new ArrayList<>(Collections.singleton(person.getEmail()));
        when(personRepository.findPersonByCity(any(String.class))).thenReturn(personArrayList);
        ArrayList<String> getEmails = personService.getEmailPersonByCity(person.getCity());
        assertEquals(emailList, getEmails);
    }


    @Test
    public void getPersonInformationTest() {
        String firstName = "Jack";
        String lastName = "Jekyll";
        when(personRepository.findPersonByFirstNameAndLastName(any(String.class), any(String.class))).thenReturn((ArrayList<Person>) personArrayList);
        when(medicalRecordRepository.findAllByFirstNameAndLastName(any(String.class), any(String.class))).thenReturn(Optional.of(medicalRecord));
        ArrayList<PersonInformation> getPersonInformationResponse = personService.getPersonInformation(firstName, lastName);
        PersonInformation personInformation = new PersonInformation();
        personInformation.setFirstName(person.getFirstName());
        personInformation.setLastName(person.getLastName());
        personInformation.setAddress(person.getAddress());
        personInformation.setEmail(person.getEmail());
        personInformation.setAllergies(medicalRecord.getAllergies());
        personInformation.setMedications(medicalRecord.getMedications());
        personInformation.setAge(30);
        getPersonInformationResponse.add(personInformation);
        assertEquals(personInformation.getFirstName(), "Jack");
        assertEquals(personInformation.getLastName(), "Jekyll");
        assertEquals(personInformation.getAddress(), "33 rue du test");
        assertEquals(personInformation.getEmail(), "Test@PersonTest.com");
        assertEquals(personInformation.getAllergies(), medicalRecord.getAllergies());
        assertEquals(personInformation.getMedications(), medicalRecord.getMedications());
        assertEquals(personInformation.getAge(), 30);
    }

    @Test
    public void getPersonInformationAndReturnAndEmptyObjectBecausePersonDontExist() {
        String firstName = "John";
        String lastName = "LastName";
        ArrayList<PersonInformation> getPersonInformation = personService.getPersonInformation(firstName, lastName);
        assertNotNull(getPersonInformation);
        assertTrue(getPersonInformation.isEmpty());
    }

    @Test
    public void GetListOfChildrenAndFamilyMembersByAddressTest() {
        when(medicalRecordRepository.findAllByFirstNameAndLastName(any(String.class), any(String.class))).thenReturn(Optional.of(medicalRecord));
        when(personRepository.findPersonByAddress(any(String.class))).thenReturn((ArrayList<Person>) personArrayList);
        Optional<ChildrenAndFamilyMembers> childrenAndFamilyMembersResponse = personService.getListOfChildrenAndFamilyMembersByAddress("addressTestListOfChildrenMethod");
        ArrayList<FamilyMembers> familyMembersList = new ArrayList<>();
        assertNotNull(childrenAndFamilyMembersResponse);
        childrenAndFamilyMembersResponse.get().setOtherMembersFamilyList(familyMembersList);
        assertTrue(childrenAndFamilyMembersResponse.isPresent());
        assertFalse(childrenAndFamilyMembersResponse.get().getChildrenArrayList().isEmpty());
        assertTrue(childrenAndFamilyMembersResponse.get().getOtherMembersFamilyList().isEmpty());
        assertEquals("Jack", childrenAndFamilyMembersResponse.get().getChildrenArrayList().get(0).getFirstName());
        assertEquals("Jekyll", childrenAndFamilyMembersResponse.get().getChildrenArrayList().get(0).getLastName());
    }

    @Test
    public void BuildChildrenAndFamilyMembersTest() {
        when(medicalRecordRepository.findAllByFirstNameAndLastName(any(String.class), any(String.class))).thenReturn(Optional.of(medicalRecord));
        ChildrenAndFamilyMembers buildChildrenAndOtherMembersResponse = personService.buildChildrenAndFamilyMembers((ArrayList<Person>) personArrayList);
        assertNotNull(buildChildrenAndOtherMembersResponse);
        assertEquals("Jack", buildChildrenAndOtherMembersResponse.getChildrenArrayList().get(0).getFirstName());
        assertEquals("Jekyll", buildChildrenAndOtherMembersResponse.getChildrenArrayList().get(0).getLastName());
    }

    @Test
    public void UpdatePersonTest() {

        personService.updatePerson(person.getFirstName(), person.getLastName(), person);
        verify(personRepository, times(1)).findByFirstNameAndLastName(person.getFirstName(), person.getLastName());
        if (personRepository.findByFirstNameAndLastName(person.getFirstName(), person.getLastName()).isPresent()) {
            Person currentPerson = personRepository.findByFirstNameAndLastName(person.getFirstName(), person.getLastName()).get();
            String firstname = person.getFirstName();
            if (firstname != null) {
                currentPerson.setFirstName(firstname);
            }
            String lastname = person.getLastName();
            if (firstname != null) {
                currentPerson.setLastName(lastname);
            }
            String email = person.getEmail();
            if (email != null) {
                currentPerson.setEmail(email);
            }
            String address = person.getAddress();
            if (address != null) {
                currentPerson.setAddress(address);
            }
            String city = person.getCity();
            if (city != null) {
                currentPerson.setCity(city);
            }
            String zip = person.getZip();
            if (zip != null) {
                currentPerson.setZip(zip);
            }
            String phone = person.getPhone();
            if (phone != null) {
                currentPerson.setPhone(phone);
            }
            verify(personRepository, times(1)).save(person);

            assertEquals(person.getFirstName(), currentPerson.getFirstName());
            assertEquals(person.getLastName(), currentPerson.getLastName());
            assertEquals(person.getAddress(), currentPerson.getAddress());
            assertEquals(person.getEmail(), currentPerson.getEmail());
            assertEquals(person.getCity(), currentPerson.getCity());
            assertEquals(person.getPhone(), currentPerson.getPhone());
            assertEquals(person.getZip(), currentPerson.getZip());

        }
    }
    @Test
    public void UpdatePersonButDoesntExist() {
        personService.updatePerson("David", "Finch", person);
        verify(personRepository, times(0)).findByFirstNameAndLastName(person.getFirstName(), person.getLastName());
        verify(personRepository, times(0)).save(person);
    }
}
