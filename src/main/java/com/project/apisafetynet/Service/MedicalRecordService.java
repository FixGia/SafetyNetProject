package com.project.apisafetynet.Service;

import com.project.apisafetynet.model.ModelRepository.MedicalRecord;

import java.util.List;
import java.util.Optional;

public interface MedicalRecordService {

  void saveMedicalRecord(List<MedicalRecord> medicalRecordList);

 void deleteMedicalRecord(MedicalRecord medicalRecord);

  Optional<MedicalRecord> getMedicalRecord(String firstName, String lastName);

  Optional<MedicalRecord> saveMedicalRecord(MedicalRecord medicalRecord);

  MedicalRecord updateMedicalRecord( MedicalRecord medicalRecord, String firstName, String lastName);

}
