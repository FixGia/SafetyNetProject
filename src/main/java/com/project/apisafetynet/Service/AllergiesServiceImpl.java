package com.project.apisafetynet.Service;

import com.project.apisafetynet.Repository.AllergiesRepository;
import com.project.apisafetynet.model.ModelRepository.Allergies;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AllergiesServiceImpl implements AllergiesService {

    final
    AllergiesRepository allergiesRepository;

    public AllergiesServiceImpl(AllergiesRepository allergiesRepository) {
        this.allergiesRepository = allergiesRepository;
    }

    @Override
    public Optional<Allergies> findByName(String nameAllergies) {
        return allergiesRepository.findAllergiesByNameAllergies(nameAllergies);
    }

    @Override
    public Allergies create(Allergies allergies) {
        return null;
    }

    @Override
    public Iterable<Allergies> saveAll(Iterable<Allergies> allergiesList) {
        return allergiesRepository.saveAll(allergiesList);

    }
}
