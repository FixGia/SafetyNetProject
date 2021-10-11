package com.project.apisafetynet.Service;

import com.project.apisafetynet.Repository.MedicationsRepository;
import com.project.apisafetynet.model.ModelRepository.Medications;
import org.springframework.stereotype.Service;

@Service
public class MedicationsServiceImp implements MedicationsService {


    final
    MedicationsRepository medicationsRepository;
    public MedicationsServiceImp(MedicationsRepository medicationsRepository) {
        this.medicationsRepository = medicationsRepository;
    }

    @Override
    public Iterable<Medications> saveAll(Iterable<Medications> medications) {
        return medicationsRepository.saveAll(medications);
    }
}
