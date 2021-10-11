package com.project.apisafetynet.Service;

import com.project.apisafetynet.model.ModelRepository.Allergies;

import java.util.Optional;

public interface AllergiesService {

    Iterable<Allergies> saveAll(Iterable<Allergies> allergiesList);
}
