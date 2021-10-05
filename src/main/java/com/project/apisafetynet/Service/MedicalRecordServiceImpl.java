package com.project.apisafetynet.Service;

import com.project.apisafetynet.Repository.MedicalRecordRepository;
import com.project.apisafetynet.model.ModelRepository.MedicalRecord;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    @Override
    public Iterable<MedicalRecord> getMedicalRecords() {
        return medicalRecordRepository.findAll();
    }

    @Override
    public MedicalRecord deleteMedicalRecord(MedicalRecord medicalRecord) {
        return null;
    }


    @Override
    public Optional<MedicalRecord> getMedicalRecord(String Id) {
        return medicalRecordRepository.findById(Id);
    }

    @Override
    public MedicalRecord saveMedicalRecord(MedicalRecord medicalRecord) {
        return medicalRecordRepository.save(medicalRecord);
    }



}
