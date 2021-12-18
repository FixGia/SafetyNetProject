package com.project.apisafetynet.Service;

import com.project.apisafetynet.model.ModelRepository.Medications;

import java.util.Optional;

public interface MedicationService {

    Iterable<Medications> saveAll(Iterable<Medications> medications);


}
