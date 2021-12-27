package com.project.apisafetynet.Service;

import com.project.apisafetynet.model.ModelRepository.Allergies;

import java.util.Optional;

public interface AllergiesService {

    Iterable<Allergies> saveAllergies(Iterable<Allergies> allergies);

}
