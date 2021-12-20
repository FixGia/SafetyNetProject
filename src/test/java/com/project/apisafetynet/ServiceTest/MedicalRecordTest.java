package com.project.apisafetynet.ServiceTest;

import com.project.apisafetynet.Repository.AllergiesRepository;
import com.project.apisafetynet.Repository.MedicalRecordRepository;
import com.project.apisafetynet.Repository.MedicationsRepository;
import com.project.apisafetynet.Service.Impl.MedicalRecordServiceImpl;
import com.project.apisafetynet.model.DTO.MedicalRecordRequest;
import com.project.apisafetynet.model.ModelRepository.Allergies;
import com.project.apisafetynet.model.ModelRepository.MedicalRecord;

import com.project.apisafetynet.model.ModelRepository.Medications;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class MedicalRecordTest {
    private List<MedicalRecord> medicalRecordList = new ArrayList<>();
    private MedicalRecord medicalRecord;
    private Allergies allergie;
    private Medications medication;
    private List<Medications> medications;
    private List<Allergies> allergies;
    @Mock
    MedicalRecordRepository medicalRecordRepository;
    @Mock
    MedicationsRepository medicationsRepository;
    @Mock
    AllergiesRepository allergiesRepository;
    @InjectMocks
    MedicalRecordServiceImpl medicalRecordService;



    @BeforeEach
    private void setUpBeforeEachTests() {
        medicalRecord = new MedicalRecord();
        medicalRecord.setId(1L);
        medicalRecord.setFirstName("Jack");
        medicalRecord.setLastName("Jekyll");
        medication= new Medications();
        medication.setNameMedication("medicationTest");
        allergie = new Allergies();
        allergie.setNameAllergies("allergiesTest");
        medications= new ArrayList<>();
        medications.add(medication);
        medicalRecord.setMedications(medications);
        allergies = new ArrayList<>();
        allergies.add(allergie);
        medicalRecord.setAllergies(allergies);
        medicalRecord.setBirthdate("03/12/1991");
        medicalRecordList.add(medicalRecord);
        lenient().when(medicationsRepository.save(medication)).thenReturn(medication);
        lenient().when(allergiesRepository.save(allergie)).thenReturn(allergie);
        lenient().when(medicalRecordRepository.findAll()).thenReturn(medicalRecordList);
        lenient().when(medicalRecordRepository.save(medicalRecord)).thenReturn(medicalRecord);
    }

    @Test
    public void saveAllMedicalRecordsTest() {
        medicalRecordService.saveMedicalRecord(medicalRecordList);
        verify(medicalRecordRepository, times(1)).saveAll(medicalRecordList);
    }
    @Test
    public void getMedicalRecordTest() {
        medicalRecordService.getMedicalRecord("Jack", "Jekyll");
        verify(medicalRecordRepository, times(1)).findAllByFirstNameAndLastName("Jack", "Jekyll");
        assertEquals(medicalRecordService.getMedicalRecord("Jack", "Jekyll"), medicalRecordRepository.findAllByFirstNameAndLastName("Jack", "Jekyll"));

    }
    @Test
    public void deleteMedicalRecordTest(){
        medicalRecordService.deleteMedicalRecord(medicalRecord);
        verify(medicalRecordRepository, times(1)).delete(medicalRecord);
    }

    @Test
    public void saveMedicalRecordTest(){
        MedicalRecord medicalRecord= new MedicalRecord();
        medicalRecordService.saveMedicalRecord(medicalRecord);
        verify(medicalRecordRepository, times(1)).save(medicalRecord);
    }

    @Test
    public void createMedicalRecordTest(){
        MedicalRecordRequest medicalRecordRequest= new MedicalRecordRequest();
        medicalRecordRequest.setFirstName("firstname");
        medicalRecordRequest.setLastName("lastname");
        medicalRecordRequest.setBirthdate("birthdate");
        MedicalRecord createMedicalRecord = medicalRecordService.createMedicalRecord(medicalRecordRequest);
        assertEquals(createMedicalRecord.getFirstName(), "firstname");
        assertEquals(createMedicalRecord.getLastName(), "lastname");
        assertEquals(createMedicalRecord.getBirthdate(), "birthdate");
        verify(medicalRecordRepository, times(1)).save(createMedicalRecord);
    }

    @Test
    public void updateMedicalRecordTest() {
        lenient().when(medicalRecordRepository.findAllByFirstNameAndLastName("Jack", "Jekyll")).thenReturn(Optional.of(medicalRecord));
        if(medicalRecord != null) {
            medicalRecordService.updateMedicalRecord(new MedicalRecordRequest(), "Jack", "Jekyll");
            verify(medicalRecordRepository, times(1)).save(medicalRecord);
        }
            else {

                verify(medicalRecordRepository, times(0)).save(medicalRecord);
            }
        }

    }

