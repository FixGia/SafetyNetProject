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
    public Iterable<Allergies> saveAll(Iterable<Allergies> allergies) {
        return allergiesRepository.saveAll(allergies);
    }
}

