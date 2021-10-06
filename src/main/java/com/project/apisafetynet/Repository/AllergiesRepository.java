package com.project.apisafetynet.Repository;

import com.project.apisafetynet.model.ModelRepository.Allergies;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AllergiesRepository extends CrudRepository<Allergies, Long> {

    Optional<Allergies> findAllergiesByNameAllergies(String nameAllergies);
}
