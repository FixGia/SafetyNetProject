package com.project.apisafetynet.Service;

import com.project.apisafetynet.model.ModelRepository.MedicalRecord;

import java.util.List;
import java.util.Optional;

public interface MedicalRecordService {

  void saveMedicalRecord(List<MedicalRecord> medicalRecordList);

  Iterable<MedicalRecord> getMedicalRecords();

  MedicalRecord deleteMedicalRecord(MedicalRecord medicalRecord);


  Optional<MedicalRecord> getMedicalRecord(String Id);

  MedicalRecord saveMedicalRecord(MedicalRecord medicalRecord);

}
