package com.project.apisafetynet.Service.Impl;

import com.project.apisafetynet.Repository.MedicationsRepository;
import com.project.apisafetynet.Service.MedicationService;
import com.project.apisafetynet.model.ModelRepository.MedicalRecord;
import com.project.apisafetynet.model.ModelRepository.Medications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MedicationServiceImpl implements MedicationService {

    private final MedicationsRepository medicationsRepository;

    public MedicationServiceImpl(MedicationsRepository medicationsRepository) {
        this.medicationsRepository = medicationsRepository;
    }


    @Override
    public Iterable<Medications> saveAll(Iterable<Medications> medications) {
        return medicationsRepository.saveAll(medications);
    }

}
