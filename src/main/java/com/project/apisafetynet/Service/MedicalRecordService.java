package com.project.apisafetynet.Service;

import com.project.apisafetynet.model.MedicalRecord;

import java.util.List;

public interface MedicalRecordService {

  void saveMedicalRecord(List<MedicalRecord> medicalRecordList);
}
