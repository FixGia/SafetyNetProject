package com.project.apisafetynet.Service;

import com.project.apisafetynet.model.ModelRepository.Medications;

public interface MedicationsService {

    Iterable<Medications> saveAll(Iterable<Medications> medications);
}
