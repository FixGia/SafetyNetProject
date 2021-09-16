package com.project.apisafetynet.Service;


import com.project.apisafetynet.model.Person;

import java.util.List;

public interface PersonService {

    void SavePersons (List<Person> personsList) ;

    Iterable<Person> getPersons();

    Person deletePerson(Person person);

    Person savePerson(Person person);

    Person getPersonByFirstNameAndLastName(Person firstname, Person lastname);

    }

