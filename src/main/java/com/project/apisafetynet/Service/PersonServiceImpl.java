package com.project.apisafetynet.Service;


import com.project.apisafetynet.Dao.LoadJsonFile;
import com.project.apisafetynet.Repository.PersonRepository;
import com.project.apisafetynet.model.Persons;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService {

    final PersonRepository personRepository;

    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

}