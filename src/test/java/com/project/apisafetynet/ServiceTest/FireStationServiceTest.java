package com.project.apisafetynet.ServiceTest;

import com.project.apisafetynet.Repository.FireStationRepository;
import com.project.apisafetynet.Repository.MedicalRecordRepository;
import com.project.apisafetynet.Repository.PersonRepository;
import com.project.apisafetynet.Service.CalculateAgeService;
import com.project.apisafetynet.Service.CalculateAgeServiceImpl;
import com.project.apisafetynet.Service.FireStationServiceImp;
import com.project.apisafetynet.model.*;
import com.project.apisafetynet.model.ModelRepository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FireStationServiceTest {

    private FireStation fireStation;
    private MedicalRecord medicalRecord;
    private Person person;
    private List<Person> personArrayList = new ArrayList<>();
    private ArrayList<FireStation> fireStationsArrayList = new ArrayList<>();
    private List<MedicalRecord> medicalRecordList = new ArrayList<>();


    @Mock
    private FireStationRepository fireStationRepository;
    @Mock
    private PersonRepository personRepository;
    @Mock
    private MedicalRecordRepository medicalRecordRepository;
    @Mock
    private CalculateAgeServiceImpl calculateAgeService;
    @InjectMocks
    private FireStationServiceImp fireStationService;


    @BeforeEach
    private void setUpBeforeAllTests() {
        person = new Person();
        person.setId("Jack,Jekyll");
        person.setFirstName("Jack");
        person.setLastName("Jekyll");
        person.setAddress("33 rue du test");
        person.setZip("0000");
        person.setPhone("00000000");
        person.setCity("TestVille");
        person.email("Test@PersonTest.com");
        personArrayList.add(person);
        fireStation = new FireStation();
        fireStation.setStation("1");
        fireStation.setAddress("1 Station address");
        fireStation.setId(1L);
        fireStationsArrayList.add(fireStation);
        medicalRecord = new MedicalRecord();
        medicalRecord.setId("Jack,Jekyll");
        medicalRecord.setFirstname("Jack");
        medicalRecord.setLastname("Jekyll");
        List<Medications> medications = new ArrayList<>();
        medicalRecord.setMedications(medications);
        List<Allergies> allergies = new ArrayList<>();
        medicalRecord.setAllergies(allergies);
        medicalRecord.setBirthdate("03/12/1991");
        medicalRecordList.add(medicalRecord);
        lenient().when(fireStationRepository.findAll()).thenReturn(fireStationsArrayList);
        lenient().when(fireStationRepository.save(fireStation)).thenReturn(fireStation);
        lenient().when(medicalRecordRepository.findAll()).thenReturn(medicalRecordList);
        lenient().when(medicalRecordRepository.save(medicalRecord)).thenReturn(medicalRecord);
        lenient().when(personRepository.findAll()).thenReturn(personArrayList);
        lenient().when(personRepository.save(person)).thenReturn(person);

    }

    @Test
    public void getFireStationsTest() {
        Iterable<FireStation> fireStationIterableList = fireStationService.getFireStations();
        assertNotNull(fireStationIterableList);
        assertEquals(fireStationsArrayList, fireStationIterableList);
    }

    @Test
    public void saveFireStationsTest() {
        fireStationService.saveFireStations(fireStationsArrayList);
        verify(fireStationRepository, times(1)).saveAll(fireStationsArrayList);
    }

    @Test
    public void saveFireStationTest() {
        fireStationService.saveFireStation(fireStation);
        verify(fireStationRepository, times(1)).save(fireStation);
    }

    @Test
    public void getFireStationTest() {
        fireStationService.getFireStation(1);
        verify(fireStationRepository, times(1)).findById(1L);
        assertEquals(fireStationService.getFireStation(1), fireStationRepository.findById(1L));
    }

    @Test
    public void deleteFireStationTest() {
        fireStationService.deleteFireStation(1);
        verify(fireStationRepository, times(1)).deleteById(1L);
    }

    @Test
    public void getListPersonInformationByFireStationTest() {

        when(fireStationRepository.findFireStationByStation(any(String.class))).thenReturn((ArrayList<FireStation>) fireStationsArrayList);
        when(personRepository.findPersonByAddress(any(String.class))).thenReturn((ArrayList<Person>) personArrayList);
        when(medicalRecordRepository.findMedicalRecordByFirstnameAndLastname(any(String.class), any(String.class))).thenReturn((ArrayList<MedicalRecord>) medicalRecordList);

        Optional<InfoByZone> infoByZone = fireStationService.getListPersonInformationByFireStation(fireStation.getStation());
        assertNotNull(infoByZone);
        assertEquals(infoByZone.get().getPersonCoveredByFireStation().get(0).getFirstName(), "Jack");
        assertEquals(infoByZone.get().getPersonCoveredByFireStation().get(0).getLastName(), "Jekyll");
        assertEquals(infoByZone.get().getPersonCoveredByFireStation().get(0).getAddress(), "33 rue du test");
        assertEquals(infoByZone.get().getPersonCoveredByFireStation().get(0).getPhone(), "00000000");
        assertEquals(infoByZone.get().getAdults(), 0);
        assertEquals(infoByZone.get().getChild(), 1);


    }

    @Test
    public void getPhoneNumberByStationTest() {
        when(fireStationRepository.findFireStationByStation(any(String.class))).thenReturn((ArrayList<FireStation>) fireStationsArrayList);
        when(personRepository.findPersonByAddress(any(String.class))).thenReturn((ArrayList<Person>) personArrayList);

        ArrayList<PhoneAlert> phoneAlerts = fireStationService.getPhoneNumberByStation(fireStation.getAddress());

        assertNotNull(phoneAlerts);
        assertEquals(phoneAlerts.get(0).getPhone(), "00000000");
    }

    @Test
    public void getPersonListAndStationNumberTest() {
        when(personRepository.findPersonByAddress(any(String.class))).thenReturn((ArrayList<Person>) personArrayList);
        when(fireStationRepository.findFireStationByAddress(any(String.class))).thenReturn(Optional.of(fireStation));
        when(medicalRecordRepository.findAllByFirstnameAndLastname(any(String.class), any(String.class))).thenReturn(Optional.of(medicalRecord));

        ArrayList<PersonsAndFireStationWhoDeservedThem> personsAndFireStationWhoDeservedThem = fireStationService.getPersonListAndStationNumber(fireStation.getAddress());

        assertNotNull(personsAndFireStationWhoDeservedThem);
        assertEquals( "Jack", personsAndFireStationWhoDeservedThem.get(0).getFirstName());
        assertEquals("allergies",personsAndFireStationWhoDeservedThem.get(0).getAllergies());
        assertEquals("1",personsAndFireStationWhoDeservedThem.get(0).getFireStationNumber());
    }

    @Test
    public void getListOfFloodsByStations() {
        lenient().when(fireStationRepository.findFireStationByAddress(any(String.class))).thenReturn(Optional.of(fireStation));
        lenient().when(personRepository.findPersonByAddress(any(String.class))).thenReturn((ArrayList<Person>) personArrayList);

        ArrayList<String> stations = new ArrayList<>();
        for (String station : stations) {
            fireStationRepository.findFireStationByStation(station);
            ArrayList<Flood> getListOfFloods = fireStationService.getListOfFloodsByStations(stations);

            assertNotNull(getListOfFloods);
            assertEquals( "Jack", getListOfFloods.get(0).getFloodMembersList().get(0).getFirstName());
            assertEquals("Jekyll", getListOfFloods.get(0).getFloodMembersList().get(0).getLastName());
            assertEquals(30,getListOfFloods.get(0).getFloodMembersList().get(0).getAge());
            assertEquals( "1 Station address",getListOfFloods.get(0).getAddress());
        }
    }
    @Test
    public void buildFloodMemberList() {
        when(medicalRecordRepository.findAllByFirstnameAndLastname(any(String.class), any(String.class))).thenReturn(Optional.of(medicalRecord));
        ArrayList<FloodMembers> floodMembersArrayList= fireStationService.buildFloodMemberList((ArrayList<Person>) personArrayList);
        assertNotNull(floodMembersArrayList);

        assertEquals("Jack", floodMembersArrayList.get(0).getFirstName());

    }
}
