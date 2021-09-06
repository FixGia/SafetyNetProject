package com.project.apisafetynet.Service;


import com.project.apisafetynet.Repository.PersonRepository;
import lombok.Data;
import org.springframework.stereotype.Service;


@Service
@Data
public class PersonServiceImpl implements PersonService {

    final PersonRepository personRepository;

    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

}