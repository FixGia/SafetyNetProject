package com.project.apisafetynet.Service;


import com.project.apisafetynet.model.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface PersonService {

    void SavePersons (List<Person> personsList) ;

    Iterable<Person> getPersons();

    void deletePerson(Person person);


    Person savePerson(Person person, String Id);

    Optional<Person> getPerson(String Id);

    ArrayList<String> getEmailPersonByCity(String city);

    ArrayList<String> getPersonInformation(String address);

    }

