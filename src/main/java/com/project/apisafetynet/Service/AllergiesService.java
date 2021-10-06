package com.project.apisafetynet.Service;

import com.project.apisafetynet.model.ModelRepository.Allergies;

import java.util.Optional;

public interface AllergiesService {

    Optional<Allergies> findByName(String nameAllergies);

    Allergies create(Allergies allergies);

    Iterable<Allergies> saveAll(Iterable<Allergies> allergiesList);
}
