package com.project.apisafetynet.Service;


import com.project.apisafetynet.model.DTO.ChildrenAndFamilyMembers;
import com.project.apisafetynet.model.ModelRepository.Person;
import com.project.apisafetynet.model.DTO.PersonRequest;
import com.project.apisafetynet.model.DTO.PersonInformation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface PersonService {

    void SavePersons (List<Person> personsList) ;

    Iterable<Person> getPersons();

    void deletePerson(Person person);

    Optional<Person> savePerson(Person person);

    Optional<Person> getPerson(String firstName, String lastName);

    ArrayList<String> getEmailPersonByCity(String city);

    ArrayList<PersonInformation> getPersonInformation(String firstName, String lastName);

    Optional<ChildrenAndFamilyMembers> getListOfChildrenAndFamilyMembersByAddress(String address);

    Person updatePerson(String firstName, String lastName, PersonRequest person);

    Person createPerson(PersonRequest personRequest);

    }

