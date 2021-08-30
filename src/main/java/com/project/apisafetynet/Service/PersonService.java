package com.project.apisafetynet.Service;

import com.project.apisafetynet.PersonRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Data
public class PersonService {

    @Autowired
    PersonRepository personRepository;

}
