package com.project.apisafetynet.Service;

import com.project.apisafetynet.Repository.MedicationsRepository;
import com.project.apisafetynet.model.ModelRepository.MedicalRecord;
import com.project.apisafetynet.model.ModelRepository.Medications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MedicationServiceImpl implements MedicationService {

    final
    MedicationsRepository medicationsRepository;

    public MedicationServiceImpl(MedicationsRepository medicationsRepository) {
        this.medicationsRepository = medicationsRepository;
    }


    @Override
    public Iterable<Medications> saveAll(Iterable<Medications> medications) {
        return medicationsRepository.saveAll(medications);
    }

    @Override
    public Optional<Medications> updateMedications(String nameMedication) {
        Optional<Medications> medications = medicationsRepository.findById(nameMedication);
        if (medications.isPresent()) {
            Medications currentMedication = medications.get();

            String nameMedications = medications.get().getNameMedication();
            if (nameMedications != null) {
                currentMedication.setNameMedication(nameMedication);
            }
        }
        return medications;
    }
}
