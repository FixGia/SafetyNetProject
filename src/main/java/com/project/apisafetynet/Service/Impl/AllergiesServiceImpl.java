package com.project.apisafetynet.Service.Impl;

import com.project.apisafetynet.Repository.AllergiesRepository;
import com.project.apisafetynet.Service.AllergiesService;
import com.project.apisafetynet.model.ModelRepository.Allergies;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AllergiesServiceImpl implements AllergiesService {

    private final AllergiesRepository allergiesRepository;

    public AllergiesServiceImpl(AllergiesRepository allergiesRepository) {
        this.allergiesRepository = allergiesRepository;
    }


    @Override
    public Iterable<Allergies> saveAllergies(Iterable<Allergies> allergies) {
        return allergiesRepository.saveAll(allergies);
    }
}

