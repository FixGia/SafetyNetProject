package com.project.apisafetynet.ServiceTest;

import com.project.apisafetynet.Repository.MedicalRecordRepository;
import com.project.apisafetynet.Service.MedicalRecordServiceImpl;
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

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class MedicalRecordTest {
    private List<MedicalRecord> medicalRecordList = new ArrayList<>();
    private MedicalRecord medicalRecord;
    @Mock
    MedicalRecordRepository medicalRecordRepository;
    @InjectMocks
    MedicalRecordServiceImpl medicalRecordService;


    @BeforeEach
    private void setUpBeforeEachTests() {
        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setId(1L);
        medicalRecord.setFirstname("Jack");
        medicalRecord.setLastname("Jekyll");
        List<Medications> medications= new ArrayList<>();
        medicalRecord.setMedications(medications);
        List<Allergies> allergies = new ArrayList<>();
        medicalRecord.setAllergies(allergies);
        medicalRecord.setBirthdate("03/12/1991");
        medicalRecordList.add(medicalRecord);
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
        verify(medicalRecordRepository, times(1)).findAllByFirstnameAndLastname("Jack", "Jekyll");
        assertEquals(medicalRecordService.getMedicalRecord("Jack", "Jekyll"), medicalRecordRepository.findAllByFirstnameAndLastname("Jack", "Jekyll"));

    }
    @Test
    public void deleteMedicalRecordTest(){
        medicalRecordService.deleteMedicalRecord(medicalRecord);
        verify(medicalRecordRepository, times(1)).delete(medicalRecord);
    }
    @Test
    public void saveMedicalRecordTest() {
        medicalRecordService.saveMedicalRecord(medicalRecord);
        verify(medicalRecordRepository, times(1)).save(medicalRecord);

    }
}