package com.project.apisafetynet.Service;

import com.project.apisafetynet.model.ModelRepository.Medications;

public interface MedicationService {

    Iterable<Medications> saveAll(Iterable<Medications> medications);

}
