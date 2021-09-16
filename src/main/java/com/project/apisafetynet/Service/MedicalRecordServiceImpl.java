package com.project.apisafetynet.Service;

import com.project.apisafetynet.Repository.MedicalRecordRepository;
import com.project.apisafetynet.model.MedicalRecord;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
public class MedicalRecordServiceImpl implements MedicalRecordService {

    private MedicalRecordRepository medicalRecordRepository;

    public MedicalRecordServiceImpl(MedicalRecordRepository medicalRecordRepository) {
        this.medicalRecordRepository = medicalRecordRepository;
    }

    @Override
    public void saveMedicalRecord(List<MedicalRecord> medicalRecordList) {
        this.medicalRecordRepository.saveAll(medicalRecordList);
    }
}
